var $_$onUsbKeyChangeCallBackFunc = null;

// set custom alert function
function SetAlertFunction(custom_alert) {
    $_$XTXAlert = custom_alert;
}

function $checkBrowserISIE() {
    return (!!window.ActiveXObject || 'ActiveXObject' in window) ? true : false;
}


//usbkey change default callback function
function $OnUsbKeyChange() {
    // console.info("gxca usb change");
    if (typeof $_$onUsbKeyChangeCallBackFunc == 'function') {
        $_$onUsbKeyChangeCallBackFunc();
    }
}

// IE11 attach event
function $AttachIE11OnUSBKeychangeEvent(strObjName) {
    var handler = document.createElement("script");
    handler.setAttribute("for", strObjName);
    handler.setAttribute("event", "OnUsbKeyChange");
    handler.appendChild(document.createTextNode("$OnUsbKeyChange()"));
    document.body.appendChild(handler);
}

//load a control
function $LoadControl(CLSID, ctlName, testFuncName, addEvent) {
    var pluginDiv = document.getElementById("pluginDiv" + ctlName);
    if (pluginDiv) {
        return true;
    }
    pluginDiv = document.createElement("div");
    pluginDiv.id = "pluginDiv" + ctlName;
    document.body.appendChild(pluginDiv);

    try {
        if ($checkBrowserISIE()) {  // IE
            pluginDiv.innerHTML = '<object id="' + ctlName + '" classid="CLSID:' + CLSID + '" style="HEIGHT:0px; WIDTH:0px"></object>';
            if (addEvent) {
                var clt = eval(ctlName);
                if (clt.attachEvent) {
                    clt.attachEvent("OnUsbKeyChange", $OnUsbKeyChange);
                } else {// IE11 not support attachEvent, and addEventListener do not work well, so addEvent ourself
                    $AttachIE11OnUSBKeychangeEvent(ctlName);
                }
            }
        } else {
            var chromeVersion = window.navigator.userAgent.match(/Chrome\/(\d+)\./);
            if (chromeVersion && chromeVersion[1]) {
                if (parseInt(chromeVersion[1], 10) >= 42) { // not support npapi return false
                    document.body.removeChild(pluginDiv);
                    pluginDiv.innerHTML = "";
                    pluginDiv = null;
                    return false;
                }
            }

            if (addEvent) {
                pluginDiv.innerHTML = '<embed id=' + ctlName + ' type=application/x-xtx-axhost clsid={' + CLSID + '} event_OnUsbkeyChange=$OnUsbKeyChange width=0 height=0 />';
            } else {
                pluginDiv.innerHTML = '<embed id=' + ctlName + ' type=application/x-xtx-axhost clsid={' + CLSID + '} width=0 height=0 />';
            }
        }

        if (testFuncName != null && testFuncName != "" && eval(ctlName + "." + testFuncName) == undefined) {
            document.body.removeChild(pluginDiv);
            pluginDiv.innerHTML = "";
            pluginDiv = null;
            return false;
        }
        return true;
    } catch (e) {
        document.body.removeChild(pluginDiv);
        pluginDiv.innerHTML = "";
        pluginDiv = null;
        return false;
    }
}

function $XTXAlert(strMsg) {
    if (typeof $_$XTXAlert == 'function') {
        $_$XTXAlert(strMsg);
    } else {
        alert(strMsg);
    }
}

function $myOKRtnFunc(retVal, cb, ctx) {
    if (typeof cb == 'function') {
        var retObj = {retVal: retVal, ctx: ctx};
        cb(retObj);
    }
    return retVal;
}

//XTXAppCOM class
function CreateXTXAppObject() {
    var bOK = $LoadControl("3F367B74-92D9-4C5E-AB93-234F8A91D5E6", "XTXAPP", "SOF_GetVersion()", true);
    if (!bOK) {
        return null;
    }

    var o = new Object();

	o.SOF_GetCertInfoByOid = function(strCert, strOID, cb, ctx) {
        var ret = XTXAPP.SOF_GetCertInfoByOid(strCert, strOID);
        return $myOKRtnFunc(ret, cb, ctx);
    };
	
    o.SOF_GetCertInfo = function (Cert, type, cb, ctx) {
        var ret = XTXAPP.SOF_GetCertInfo(Cert, type);
        return $myOKRtnFunc(ret, cb, ctx);
    }

    o.SOF_GetUserList = function (cb, ctx) {
        var ret = XTXAPP.SOF_GetUserList();
        return $myOKRtnFunc(ret, cb, ctx);
    }

    o.SOF_SignData = function (CertID, InData, cb, ctx) {
        var ret = XTXAPP.SOF_SignData(CertID, InData);
        return $myOKRtnFunc(ret, cb, ctx);
    }

    o.SOF_SignFile = function (CertID, InFile, cb, ctx) {
        var ret = XTXAPP.SOF_SignFile(CertID, InFile);
        return $myOKRtnFunc(ret, cb, ctx);
    }

    o.SOF_ExportUserCert = function (CertID, cb, ctx) {
        var ret = XTXAPP.SOF_ExportUserCert(CertID);
        return $myOKRtnFunc(ret, cb, ctx);
    }
	
	o.SOF_Login = function(CertID,PassWd,cb,ctx){
		var ret = XTXAPP.SOF_Login(CertID,PassWd);
		return $myOKRtnFunc(ret, cb, ctx);
	}
	
	o.SOF_GetPinRetryCount = function(CertID,cb,ctx){
		var ret = XTXAPP.SOF_GetPinRetryCount(CertID);
		return $myOKRtnFunc(ret, cb, ctx);
	}

    return o;
}


//webSocket client class
function CreateWebSocketObject(myonopen, myonerror) {

    var o = new Object();


    o.ws_obj = null;
    o.ws_heartbeat_id = 0;
    o.ws_queue_id = 0; // call_cmd_id
    o.ws_queue_list = {};  // call_cmd_id callback queue
    o.ws_queue_ctx = {};
    o.xtx_version = "";

    o.load_websocket = function () {

        var ws_url = "ws://127.0.0.1:21051/xtxapp/";
        try {
			if(window.g_ws && window.g_ws.close){
				window.g_ws.close();
			}
            o.ws_obj = new WebSocket(ws_url);
			window.g_ws = o.ws_obj;
        } catch (e) {
            console.log(e);
            return false;
        }

        o.ws_queue_list["onUsbkeyChange"] = $OnUsbKeyChange;

        o.ws_obj.onopen = function (evt) {
            if (myonopen) {
                myonopen();
            }

        };

        o.ws_obj.onerror = function (evt) {
            if (myonerror) {
                myonerror();
            }
        };

        o.ws_obj.onclose = function (evt) {

        };


        o.ws_obj.onmessage = function (evt) {

            var res = JSON.parse(evt.data);
            if (res['set-cookie']) {
                document.cookie = res['set-cookie'];
            }

            //登录失败
            if (res['loginError']) {
                alert(res['loginError']);
            }

            var call_cmd_id = res['call_cmd_id'];
            if (!call_cmd_id) {
                return;
            }

            var execFunc = o.ws_queue_list[call_cmd_id];
            if (typeof(execFunc) != 'function') {
                return;
            }

            var ctx = o.ws_queue_ctx[res['call_cmd_id']];
            ctx = ctx || {returnType: "string"};

            var ret;
            if (ctx.returnType == "bool") {
                ret = res.retVal == "true" ? true : false;
            }
            else if (ctx.returnType == "number") {
                ret = Number(res.retVal);
            }
            else {
                ret = res.retVal;
            }
            var retObj = {retVal: ret, ctx: ctx};

            execFunc(retObj);

            if (res['call_cmd_id'] != "onUsbkeyChange") {
                delete o.ws_queue_list[res['call_cmd_id']];
            }
            delete o.ws_queue_ctx[res['call_cmd_id']];

        };

        return true;
    };


    o.sendMessage = function (sendMsg) {
        if (o.ws_obj.readyState == WebSocket.OPEN) {
            o.ws_obj.send(JSON.stringify(sendMsg));
        } else {
            setTimeout(function () {
                if (sendMsg.count) {
                    sendMsg.count++;
                    if (sendMsg.count === 4) {
                        return;
                    }
                }
                else {
                    sendMsg.count = 1;
                }
                o.sendMessage(sendMsg);
            }, 500);
            console.log("Can't connect to WebSocket server!");
        }
    };

    o.callMethod = function (strMethodName, cb, ctx, returnType, argsArray) {
        o.ws_queue_id++;
        if (typeof(cb) == 'function') {
            o.ws_queue_list['i_' + o.ws_queue_id] = cb;
            ctx = ctx || {};
            ctx.returnType = returnType;
            o.ws_queue_ctx['i_' + o.ws_queue_id] = ctx;
        }

        var sendArray = {};
        sendArray['cookie'] = document.cookie;
        sendArray['xtx_func_name'] = strMethodName;
        sendArray['call_cmd_id'] = 'i_' + o.ws_queue_id;


        if (arguments.length > 4) {
            sendArray["param"] = argsArray;
        }
        o.sendMessage(sendArray);

    };
	
	o.SOF_GetCertInfoByOid = function(strCert, strOID, cb, ctx) {
        var paramArray = [strCert, strOID];
        o.callMethod('SOF_GetCertInfoByOid', cb, ctx, "string", paramArray);
    };

    o.SOF_GetCertInfo = function (Cert, type, cb, ctx) {
        var paramArray = [Cert, type];
        o.callMethod('SOF_GetCertInfo', cb, ctx, "string", paramArray);
    }

    o.SOF_GetUserList = function (cb, ctx) {
        var paramArray = [];
        o.callMethod('SOF_GetUserList', cb, ctx, "string", paramArray);
    }

    o.SOF_SignData = function (CertID, InData, cb, ctx) {
        var paramArray = [CertID, InData];
        o.callMethod('SOF_SignData', cb, ctx, "string", paramArray);
    }

    o.SOF_SignFile = function (CertID, InFile, cb, ctx) {
        var paramArray = [CertID, InFile];
        o.callMethod('SOF_SignFile', cb, ctx, "string", paramArray);
    }

    o.SOF_ExportUserCert = function (CertID, cb, ctx) {
        var paramArray = [CertID];
        o.callMethod('SOF_ExportUserCert', cb, ctx, "string", paramArray);
    }

	o.SOF_Login = function(CertID,PassWd,cb,ctx){
		var paramArray = [CertID,PassWd];
		o.callMethod('SOF_Login', cb, ctx, "bool", paramArray);
	}
	
	o.SOF_GetPinRetryCount = function(CertID,cb,ctx){
		var paramArray = [CertID];
		o.callMethod('SOF_GetPinRetryCount', cb, ctx, "number", paramArray);
	}

    if (!o.load_websocket()) {
        return null;
    }
    return o;
}


   