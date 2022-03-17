const ws = require("ws")
const wsUri = '/yc-rpa/ws'

module.exports = function wsInit(wsUrl, token, mainWindow) {
    const sock = new ws(`ws://${wsUrl}${wsUri}`, {
            "headers":
                {
                    'Sec-WebSocket-Rpa-User-Token': token
                }
        }
    )
    sock.on("open", function () {
        // console.log(`connect ${wsUrl}${wsUri} success`)
        mainWindow.loadFile("view/index.html")
    })
    sock.on("error", function (err) {
        console.log("error: ", err)
    })
    sock.on("close", function () {
        // console.log(`close ${wsUrl}${wsUri} connection`)
    })
    sock.on("message", function (data) {
        mainWindow.webContents.send('ws-reply', data.toString())
    })
    return sock
}


