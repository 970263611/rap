const {ipcRenderer} = require('electron')
const {chromium, firefox, webkit, devices} = require('playwright')
const {test, expect} = require('@playwright/test')
const fs = require("fs");
const xlsx = require('node-xlsx');
const http = require('http')
const os = require('os')
const path = require('path')
const desktopPath = os.homedir() + '/Desktop/'
let remoteUrl = null

ipcRenderer.on('ws-reply', (event, arg) => {
    eval(arg)
})

ipcRenderer.on('auth', (event, auth) => {
    const iframe = document.createElement('iframe')
    remoteUrl = auth.url
    remoteUrl = remoteUrl.startsWith('http://') || remoteUrl.startsWith('https://') ? remoteUrl : 'http://' + remoteUrl
    iframe.src = remoteUrl + "/view/index"
    iframe.id = 'iframe'
    document.body.appendChild(iframe)
    setTimeout(function () {
        document.getElementById('iframe').contentWindow.postMessage(auth.token, remoteUrl)
    }, 1000)
})

function getCase(id) {
    if (remoteUrl) {
        ipcRenderer.send('ws', id)
    }
}

