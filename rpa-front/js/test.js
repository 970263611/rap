import os from "os";
import fs from "fs";
import xlsx from "node-xlsx";
import {chromium} from "playwright";
import http from "http";

/**
 * 以下为测试使用
 */
function excel() {
    const downloadFile = os.homedir() + '/Downloads/测试.xlsx'
    fs.unlink(downloadFile, function (err) {
        if (err) throw err
    })

    fs.rename(downloadFile, desktopPath + "1.xlsx", function (err) {
        if (err) throw err;
    })
    let workbook = xlsx.parse(desktopPath + "1.xlsx")
    const sheet = workbook[0].data
    const data = []
    for (let i = 0; i < sheet.length; i++) {
        const col1 = sheet[i][0]
        const col2 = sheet[i][1]
        const col3 = col1 + col2
        data.push([col1, col2, col3])
    }
    let buffer = xlsx.build([
        {
            name: '测试数据',
            data: data
        }
    ])
    fs.writeFileSync(desktopPath + '2.xlsx', buffer, {'flag': 'w'})
}

function save() {
    (async () => {
        const browser = await chromium.launch({
            headless: false,
            downloadsPath: os.homedir() + '/Downloads/temp',
            executablePath: 'C:/Program Files/Google/Chrome/Application/chrome.exe'
        })
        const context = await browser.newContext({storageState: 'C:/Users/dahua/Desktop/state.json'})
        const page = await context.newPage()
        await page.goto('https://www.wps.cn/')
        const [page1] = await Promise.all([
            page.waitForEvent('popup'),
            page.locator('text=金山文档 多人实时协作更轻松… 支持多人实时查看/编辑，让协作更轻松').click()
        ])
        await page1.locator('text=进入网页版').first().click()
        await page1.goto('https://www.kdocs.cn/?from=docs&show=all')
        const [page2] = await Promise.all([
            page1.waitForEvent('popup'),
            page1.locator('[aria-label="\\31 "] div[role="link"] >> text=1').click()
        ]);
        await page2.locator('.icons.icons-16.icons-16-topbar_more').click();
        const [download] = await Promise.all([
            page2.waitForEvent('download'),
            page2.locator('text=下载').click()
        ]);
        const downloadPath = await download.path()
        console.log(downloadPath)
        await download.saveAs(desktopPath + '2.xlsx')
    })()
}

function youdao() {
    (async () => {
        const browser = await chromium.launch({
            headless: false,
            // downloadsPath: os.homedir() + '/Downloads/temp',
            executablePath: 'C:/Program Files/Google/Chrome/Application/chrome.exe'
        })
        const context = await browser.newContext({storageState: 'C:/Users/dahua/Desktop/youdao.json'})
        const page = await context.newPage()
        await page.goto('https://note.youdao.com/web/');

        await page.locator('.ad-screen-content .ad-close svg use').click();

        await page.locator('[placeholder="搜索全部笔记"]').click();
        await Promise.all([
            page.locator('[placeholder="搜索全部笔记"]').fill('rpa测试')
        ]);
        await page.locator('span:has-text("rpa测试")').click();
        await page.frameLocator('#bulb-editor').locator('text=1.xlsx').click({
            button: 'right'
        });
        const [download] = await Promise.all([
            page.waitForEvent('download'),
            page.locator('text=附件另存为').click()
        ]);
        const downloadPath = await download.path()
        console.log(downloadPath)
        await download.saveAs(desktopPath + '3.xlsx')
    })()
}

function sendHttp(options) {
    const response = http.request(options, function (response) {
        response.on('data', function (data) {
            eval(data.toString())
        })
    })
    response.end()
}