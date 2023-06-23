#coding=utf-8
from burp import IBurpExtender
from burp import IHttpListener
import sys

class BurpExtender(IBurpExtender, IHttpListener):
    def registerExtenderCallbacks(self, callbacks):
        # 获取Burp插件API的回调对象
        self._callbacks = callbacks
        # 获取用于处理HTTP请求和响应的工具
        self._helpers = callbacks.getHelpers()
        # 设置插件名称
        callbacks.setExtensionName("Custom Content Printer")
        # 注册HTTP监听器
        callbacks.registerHttpListener(self)
        # 重定向标准输出到Burp Suite的Extender-Output窗口
        sys.stdout = callbacks.getStdout()

    def processHttpMessage(self, toolFlag, messageIsRequest, messageInfo):
        # 检查是否为代理拦截的请求
        if toolFlag == self._callbacks.TOOL_PROXY:
            if messageIsRequest:
                # 获取请求消息
                request = messageInfo.getRequest()
                # 解析请求消息
                analyzedRequest = self._helpers.analyzeRequest(request)
                # 获取请求头
                headers = analyzedRequest.getHeaders()
                # 获取请求体
                body = request[analyzedRequest.getBodyOffset():]
                # 将请求体转换为字符串
                bodyStr = self._helpers.bytesToString(body)
                # 检查请求体中是否包含指定字符串
                if "OVZWK4TOMFWWKPKHKVCVGVBGOBQXG43XN5ZGIPKUIVGVAI2QIFJVGV2E" in bodyStr:
                    # 将请求体中的字符串替换为新值
                    modifiedBodyStr = bodyStr.replace("OVZWK4TOMFWWKPKHKVCVGVBGOBQXG43XN5ZGIPKUIVGVAI2QIFJVGV2E", "OVZWK4TOMFWWKPKHKVCVGVBGOBQXG43XN5ZGIPKUIVGVAX2QIFJVGV2E")
                    modifiedBody = self._helpers.stringToBytes(modifiedBodyStr)
                    # 更新请求消息的内容
                    modifiedRequest = self._helpers.buildHttpMessage(headers, modifiedBody)
                    messageInfo.setRequest(modifiedRequest)
                    body = modifiedBody
                # 打印请求内容到Extender-Output
                self.print_content(headers, body)
            else:
                # 获取响应消息
                response = messageInfo.getResponse()
                # 解析响应消息
                analyzedResponse = self._helpers.analyzeResponse(response)
                # 获取响应头
                headers = analyzedResponse.getHeaders()
                # 获取响应体
                body = response[analyzedResponse.getBodyOffset():]
                # 打印响应内容到Extender-Output
                self.print_content(headers, body)

    def print_content(self, headers, body):
        # 将请求/响应内容转换为字符串
        headersStr = self._helpers.bytesToString(b"".join(headers))
        bodyStr = self._helpers.bytesToString(body)
        # 打印到Extender-Output窗口
        print("[Headers]\n" + headersStr + "\n")
        print("[Body]\n" + bodyStr + "\n")
        print("--------------------------------------------------" + "\n")
