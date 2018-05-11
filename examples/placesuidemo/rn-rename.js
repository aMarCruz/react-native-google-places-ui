#!/usr/bin/env node

/* eslint-env node, es6 */

//import replace from 'node-replace'
const fs = require('fs')
const path = require('path')
const readline = require('readline')
const rimraf = require('rimraf')
const pjson = require('./package.json')

const oldInfo = {
  appName: 'Google Places UI Demo',
  projectName: 'GooglePlacesUIDemo',
  packageName: 'placesuidemo',
  packageID: 'com.placesuidemo',
}

const newInfo = {
  appName: '',
  projectName: '',
  packageName: '',
  packageID: '',
  androidPathToModule: '',
}

const ROOT = process.cwd()

function fullPath (file) {
  if (!path.isAbsolute(file)) {
    file = path.join(ROOT, file)
  }
  return path.normalize(file)
}

function relative (file) {
  return path.relative(ROOT, file)
}

function readSync (file) {
  return fs.readFileSync(fullPath(file), 'utf8')
}

const deleteDirectory = (dir) => {
  return new Promise((resolve, reject) => {
    rimraf(dir, { glob: false }, (error) => {
      if (error) {
        reject(error)
      } else {
        resolve(true)
      }
    })
  })
}

function cleanBuildDirectories () {
  const directoriesToDelete = [
    path.join(ROOT, 'ios/build/*'),
    path.join(ROOT, 'android/.gradle/*'),
    path.join(ROOT, 'android/app/build/*'),
    path.join(ROOT, 'android/build/*'),
  ]
  console.log('Removing build directories...')
  return Promise.all(directoriesToDelete.map(deleteDirectory)).then(() => {
    console.log('Done removing build directories.')
  })
}

// ----------------------------------------------------------------------------
//  Renaming / moving files
// ----------------------------------------------------------------------------

function renamePackageFiles () {
  return new Promise((resolve) => {
    const parents = 'android/app/src/main/java/'
    const oldPath = fullPath(parents + oldInfo.packageID.split('.').join('/'))
    const newPath = fullPath(parents + newInfo.packageID.split('.').join('/'))

    console.log('Renaming android path...')
    console.log(`From ${relative(oldPath)}`)
    fs.renameSync(oldPath, newPath)
    console.log(`  to ${relative(newPath)}`)

    resolve()
  })
}

function renameProjectFiles () {
  return new Promise((resolve) => {
    const oldPackageName = oldInfo.packageName
    const newPackageName = newInfo.packageName

    console.log('Renaming iOS project files...')
    renameFilesIOS(oldPackageName, newPackageName)
    renameFilesIOS(oldPackageName.toLowerCase(), newPackageName.toLowerCase())

    console.log('Done with iOS project files.')
    resolve()
  })
}

// Move files and folders
function renameFilesIOS (oldPackageName, newPackageName) {
  const filesAndFolders = [
    `ios/${oldPackageName}.xcodeproj/xcshareddata/xcschemes/<?>-tvOS.xcscheme`,
    `ios/${oldPackageName}.xcodeproj/xcshareddata/xcschemes/<?>.xcscheme`,
    'ios/<?>.xcodeproj',
    `ios/${oldPackageName}/<?>.entitlements`,
    'ios/<?>',
    'ios/<?>-tvOS',
    'ios/<?>-tvOSTests',
    `ios/${oldPackageName}Tests/<?>Tests.m`,
    'ios/<?>Tests',
    'ios/<?>.xcworkspace',
  ]

  for (let i = 0; i < filesAndFolders.length; i++) {
    const element = filesAndFolders[i]
    const srcFile = fullPath(element.replace('<?>', oldPackageName))

    console.log(`From ${relative(srcFile)}`)
    if (fs.existsSync(srcFile)) {
      const destFile = fullPath(element.replace('<?>', newPackageName))

      fs.renameSync(srcFile, destFile)
      console.log(`  to ${relative(destFile)}`)
    } else {
      console.log(`${srcFile} does not exists.`)
    }
  }
}

// ----------------------------------------------------------------------------
//  Replacing (assume files are already renamed)
// ----------------------------------------------------------------------------

function _replace (regex, replacement, file) {
  const stats = fs.lstatSync(file)
  if (stats.isSymbolicLink()) {
    return
  }
  if (stats.isFile()) {
    let text = fs.readFileSync(file, 'utf-8')
    const ok = typeof regex === 'string' ? text.includes(regex) : regex.test(text)

    if (ok) {
      text = text.replace(regex, replacement)
      console.log(`updating ${relative(file)}`)
      fs.writeFileSync(file, text, 'utf8')
    }
  } else {
    console.log(`${relative(file)} is not a file.`)
  }
}

function replaceInFiles (regex, replacement, paths) {
  paths.forEach((file) => {
    file = fullPath(file)
    if (fs.existsSync(file)) {
      _replace(regex, replacement, file)
    } else {
      console.log(relative(file) + ' does not exists.')
    }
  })
}

function updateAppName () {
  return new Promise((resolve) => {
    const iosBase = newInfo.packageName
    const oldAppName = `(${oldInfo.appName}|${oldInfo.packageName})`
    const newAppName = newInfo.appName
    console.log(`Changing app name from "${oldAppName}" to "${newAppName}"`)

    replaceInFiles(RegExp(`<string name="app_name">${oldAppName}"<`), `<string name="app_name">${newAppName}"<`, [
      'android/app/src/main/res/values/strings.xml',
    ])
    replaceInFiles(RegExp(`"displayName": "${oldAppName}"`), `"displayName": "${newAppName}"`, [
      'app.json',
    ])
    replaceInFiles(RegExp(`text="${oldAppName}"`, 'g'), `text="${newAppName}"`, [
      `ios/${iosBase}/Base.lproj/LaunchScreen.xib`,
    ])
    replaceInFiles(RegExp(`>${oldAppName}<`, 'g'), `>${newAppName}<`, [
      `ios/${iosBase}/Info.plist`,
    ])

    console.log('App name is updated.\n')
    resolve()
  })
}

/**
 * Replaces "GooglePlacesUIDemo"
 */
function updateProjectName () {
  //const oldProjectName = oldInfo.projectName
  //const newProjectName = newInfo.projectName
  //console.log(`Updating project name from "${oldProjectName}" to "${newProjectName}"`)
  //
  //replaceInFiles(oldProjectName, newProjectName, [
  //  `ios/${newProjectName}.xcodeproj/project.pbxproj`,
  //  `ios/${newProjectName}.xcworkspace/contents.xcworkspacedata`,
  //  `ios/${newProjectName}.xcodeproj/xcshareddata/xcschemes/${newProjectName}-tvOS.xcscheme`,
  //  `ios/${newProjectName}.xcodeproj/xcshareddata/xcschemes/${newProjectName}.xcscheme`,
  //  `ios/${newProjectName}/AppDelegate.m`,
  //  `ios/${newProjectName}Tests/${newProjectName}Tests.m`,
  //  'ios/build/info.plist',
  //  'ios/Podfile',
  // ])
  //
  //console.log('Finished updating project name.\n')
  return Promise.resolve()
}

/**
 * Replaces "placesuidemo"
 */
function updatePackageName () {
  return new Promise((resolve) => {
    const oldPackageName = oldInfo.packageName
    const newPackageName = newInfo.packageName
    const androidPath = newInfo.androidPathToModule
    const iOSFolder = newInfo.packageName
    console.log(`Changing package name from ${oldPackageName} to ${newPackageName}`)

    replaceInFiles(RegExp(`(?=(['"])).${oldPackageName}(?=['"])`, 'g'), `$1${newPackageName}`, [
      `${androidPath}/MainActivity.java`,
      'android/settings.gradle',
      'app.json',
      'index.js',
      'index.android.js',
      'index.ios.js',
      `ios/${iOSFolder}/AppDelegate.m`,
      'package.json',
    ])

    replaceInFiles(RegExp(`(?=([ :'"/])).${oldPackageName}(?=[ .;'"T/-])`, 'g'), `$1${newPackageName}`, [
      `ios/${iOSFolder}.xcodeproj/project.pbxproj`,
      `ios/${iOSFolder}.xcodeproj/xcshareddata/xcschemes/${iOSFolder}-tvOS.xcscheme`,
      `ios/${iOSFolder}.xcodeproj/xcshareddata/xcschemes/${iOSFolder}.xcscheme`,
      `ios/${iOSFolder}.xcworkspace/contents.xcworkspacedata`,
      `ios/${iOSFolder}Tests/${iOSFolder}Tests.m`,
    ])

    console.log('Package name is updated.\n')
    resolve()
  })
}

/**
 * Replaces "com.placesuidemo"
 */
function updatePackageID () {
  return new Promise((resolve) => {
    const oldPackageID = oldInfo.packageID
    const newPackageID = newInfo.packageID
    const androidPath = newInfo.androidPathToModule
    console.log(`Changing package ID from "${oldPackageID}" to "${newPackageID}"...`)

    replaceInFiles(RegExp(`(?=([ "'])).${oldPackageID}(?=[;"'])`), `$1${newPackageID}`, [
      'android/app/BUCK',
      'android/app/build.gradle',
      'android/app/src/main/AndroidManifest.xml',
      `${androidPath}/MainActivity.java`,
      `${androidPath}/MainApplication.java`,
    ])

    console.log('Package ID is updated.\n')
    resolve()
  })
}

// ----------------------------------------------------------------------------
//  Main
// ----------------------------------------------------------------------------

function checkSourceInfo () {
  return new Promise((resolve, reject) => {
    let ok = pjson.name === oldInfo.packageName
    ok = ok && readSync('android/app/src/main/AndroidManifest.xml').includes(`package="${oldInfo.packageID}"`)

    if (!ok) {
      reject(new Error('The App name is already modified.'))
      return
    }

    console.log()
    console.log('---------------------------------------------------------')
    console.log('New project parameters:')
    console.log('---------------------------------------------------------')
    console.log('App display name :', newInfo.appName)
    console.log('Project name     :', newInfo.projectName)
    console.log('Package name     :', newInfo.packageName)
    console.log('Package id       :', newInfo.packageID)
    console.log('---------------------------------------------------------')

    let r = null
    try {
      r = readline.createInterface({
        input: process.stdin,
        output: process.stdout,
      })
      r.question('Is this correct?', (answer) => {
        r.close()
        resolve(answer)
      })
    } catch (e) {
      if (r) {
        r.close()
      }
      reject(e)
    }
  })
}

function run () {
  const appName = process.argv[2]
  const packageID = process.argv[3]

  if (!appName || !packageID) {
    console.log(`Usage: ${relative(process.argv[1])} "{App name}" {new.package.name}`)
    process.exit(1)
    return
  }

  if (!/^[a-zA-Z\s][a-zA-Z0-9\s]+$/.test(appName) || !/^[a-z]{2,}\.[\w.]+$/i.test(packageID)) {
    console.log('ERROR: Invalid appName or Bundle Identifier.')
    console.log('       Use something like "My App" com.myname')
    process.exit(1)
    return
  }

  newInfo.appName = appName
  newInfo.projectName = appName.replace(/\s+/g, '')
  newInfo.packageName = packageID.split('.').pop()
  newInfo.packageID = packageID
  newInfo.androidPathToModule = fullPath('android/app/src/main/java/' + packageID.split('.').join('/'))

  checkSourceInfo()
    .then(() => renamePackageFiles())
    .then(() => renameProjectFiles())
    .then(() => updateAppName())
    .then(() => updateProjectName())
    .then(() => updatePackageName())
    .then(() => updatePackageID())
    .then(() => cleanBuildDirectories())
    .then(() => {
      console.log()
      console.log('---------------------------------------------------------')
      console.log('Done.')
      console.log()
    }).catch((error) => {
      console.error('' + error)
      process.exit(1)
    })
}

run()
