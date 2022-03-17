const {app, BrowserWindow, Menu, ipcMain} = require('electron')
const path = require('path')
const wsInit = require("./js/wb.js")
const remoteUrl = "localhost:8000"

try {
    require('electron-reloader')(module)
} catch (_) {
}

let mainWindow
let webSocket
let token
ipcMain.on('login', (event, wsToken) => {
    token = wsToken
    webSocket = wsInit(remoteUrl, wsToken, mainWindow)
})

ipcMain.on('ws', (event, message) => {
    webSocket.send(message)
})

function createWindow() {
    Menu.setApplicationMenu(null)
    mainWindow = new BrowserWindow({
        width: 800,
        height: 600,
        webPreferences: {
            nodeIntegration: true,
            contextIsolation: false,
            preload: path.join(__dirname, 'js/preload.js')
        }
    })
    mainWindow.loadFile("view/login.html")
    mainWindow.webContents.on('did-finish-load', () => {
        mainWindow.webContents.send('auth', {
            url: remoteUrl,
            token: token
        })
        mainWindow.webContents.openDevTools()
    })
}

app.whenReady().then(() => {
    createWindow()
    app.on('activate', function () {
        if (BrowserWindow.getAllWindows().length === 0) createWindow()
    })
})

app.on('window-all-closed', function () {
    if (process.platform !== 'darwin') app.quit()
})