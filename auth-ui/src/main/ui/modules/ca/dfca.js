

/**
根据浏览器创建WGUKey控件
**/
(function () {

    if (typeof (WGUKey) == 'undefined') {

        var msie = /msie/.test(navigator.userAgent.toLowerCase()) || /rv:\d+\.0\S+\s+like\sgecko/.test(navigator.userAgent.toLowerCase());

        if (msie) {
            // IE浏览器
            window.WGUKey = document.createElement('OBJECT');
            WGUKey.id = 'WGUKey';
            WGUKey.data = 'data:application/x-oleobject;base64,7rU6JZ20LkCKhnD/kuq4nxAHAADYEwAA2BMAAA==';
            WGUKey.classid = 'clsid:253AB5EE-B49D-402E-8A86-70FF92EAB89F';
            WGUKey.VIEWASTEXT = 'VIEWASTEXT';
            var headElem = document.getElementsByTagName('head')[0];
            headElem.insertBefore(WGUKey, headElem.childNodes[0]);
        }
        else {

            function addControl() {
                if (typeof (WGUKey) != 'undefined') {
                    clearInterval(addControlInterval);
                    return;
                }

                if (document.body && document.body.childNodes) {
                    // FF、Chrome浏览器
                    window.WGUKey = document.createElement('OBJECT');
                    WGUKey.id = 'WGUKey';
                    WGUKey.setAttribute('TYPE', 'WallGreat-x-itst-activex');
                    WGUKey.data = 'data:application/x-oleobject;base64,7rU6JZ20LkCKhnD/kuq4nxAHAADYEwAA2BMAAA==';
                    WGUKey.setAttribute('clsid', '{253AB5EE-B49D-402E-8A86-70FF92EAB89F}');

                    WGUKey.setAttribute('progid', 'WallGreatUKey.UKey');
                    WGUKey.setAttribute('viewastext', 'viewastext');

                    WGUKey.style.width = 0;
                    WGUKey.style.height = 0;
                    WGUKey.style.display = 'none';
                    document.body.insertBefore(WGUKey, document.body.childNodes[0]);
                }
            }

            addControl();
            var addControlInterval = setInterval(addControl, 1);
        }

    }

})();






//获取签名证书
function getCertData(pszContainerName) {
    var b = ReadCertData(1, pszContainerName);
    return b == false ?  "" : b;
}

function trim(str, is_global) {
    var result;
    result = str.replace(/(^\s+)|(\s+$)/g, "");
    if (is_global.toLowerCase() == "g") {
        result = result.replace(/\s/g, "");
    }
    return result;
}



function utf16to8(str) {
    var out, i, len, c;

    out = "";
    len = str.length;
    for (i = 0; i < len; i++) {
        c = str.charCodeAt(i);
        if ((c >= 0x0001) && (c <= 0x007F)) {
            out += str.charAt(i);
        } else if (c > 0x07FF) {
            out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
            out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
            out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
        } else {
            out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
            out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
        }
    }
    return out;
}

function utf8to16(str) {
    var out, i, len, c;
    var char2, char3;

    out = "";
    len = str.length;
    i = 0;
    while (i < len) {
        c = str.charCodeAt(i++);
        switch (c >> 4) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                // 0xxxxxxx
                out += str.charAt(i - 1);
                break;
            case 12:
            case 13:
                // 110x xxxx 10xx xxxx
                char2 = str.charCodeAt(i++);
                out += String.fromCharCode(((c & 0x1F) << 6) | (char2 & 0x3F));
                break;
            case 14:
                // 1110 xxxx 10xx xxxx 10xx xxxx
                char2 = str.charCodeAt(i++);
                char3 = str.charCodeAt(i++);
                out += String.fromCharCode(((c & 0x0F) << 12) |
					((char2 & 0x3F) << 6) |
					((char3 & 0x3F) << 0));
                break;
        }
    }

    return out;
}
var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
var base64DecodeChars = new Array(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
	52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
	15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
	41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);

function base64encode(str) {
    var out, i, len;
    var c1, c2, c3;

    len = str.length;
    i = 0;
    out = "";
    while (i < len) {
        c1 = str.charCodeAt(i++) & 0xff;
        if (i == len) {
            out += base64EncodeChars.charAt(c1 >> 2);
            out += base64EncodeChars.charAt((c1 & 0x3) << 4);
            out += "==";
            break;
        }
        c2 = str.charCodeAt(i++);
        if (i == len) {
            out += base64EncodeChars.charAt(c1 >> 2);
            out += base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
            out += base64EncodeChars.charAt((c2 & 0xF) << 2);
            out += "=";
            break;
        }
        c3 = str.charCodeAt(i++);
        out += base64EncodeChars.charAt(c1 >> 2);
        out += base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
        out += base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >> 6));
        out += base64EncodeChars.charAt(c3 & 0x3F);
    }
    return out;
}

function base64decode(str) {
    //alert(str);
    //alert(str.length);
    var c1, c2, c3, c4;
    var i, len, out;

    len = str.length;
    //alert(len);
    i = 0;
    out = "";

    while (i < len) {
        /* c1 */
        do {
            c1 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
        } while (i < len && c1 == -1);
        if (c1 == -1)
            break;

        /* c2 */
        do {
            c2 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
        } while (i < len && c2 == -1);
        if (c2 == -1)
            break;

        out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));

        /* c3 */
        do {
            c3 = str.charCodeAt(i++) & 0xff;
            if (c3 == 61)
                return out;
            c3 = base64DecodeChars[c3];
        } while (i < len && c3 == -1);
        if (c3 == -1)
            break;

        out += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));

        /* c4 */
        do {
            c4 = str.charCodeAt(i++) & 0xff;
            if (c4 == 61)
                return out;
            c4 = base64DecodeChars[c4];
        } while (i < len && c4 == -1);
        if (c4 == -1)
            break;
        out += String.fromCharCode(((c3 & 0x03) << 6) | c4);
    }
    return out;
}
var domain = "http://127.0.0.1:64737";

window.WallGreatUKey = {
    //连接key
    ConnectUK: function (keyConnectType, currentSessionKey) {
        var ajaxResult = $.ajax({
            type: "POST",
            url: domain + "/ConnectUK",
            async: false,
            dataType: "json",
            data: {
                "UKeyName": keyConnectType,
                "SessionId": currentSessionKey
            },
            success: function (data) {
                //返回成功标识
            }
        });

        return ajaxResult.responseText;
    },


    //读取证书数据
    UKReadCertData: function (dwCertFlag, pszContainerName, currentSessionKey) {
        var ajaxResult = $.ajax({
            type: "POST",
            url: domain + "/UKReadCertData",
            async: false,
            dataType: "json",
            data: {
                "CertFlag": dwCertFlag,
                "ContainerName": pszContainerName,
                "SessionId": currentSessionKey
            },
            success: function (data) {
                // jsondata.Data1;  返回证书数据
            }
        });
        return ajaxResult.responseText;
    },


    //获取证书信息
    UKGetUkeyCertInfo: function (dwCertFlag, dataFlag, pszContainerName, currentSessionKey) {
        var ajaxResult = $.ajax({
            type: "POST",
            url: domain + "/UKGetUkeyCertInfo",
            async: false,
            dataType: "json",
            data: {
                "CertFlag": dwCertFlag,
                "DataFlag": dataFlag,
                "ContainerName": pszContainerName,
                "SessionId": currentSessionKey
            },
            success: function (data) {
                //返回证书数据
            }
        });
        return ajaxResult.responseText;
    },
    //解析证书扩展项
    UKGetCertExtensions: function (szCert, szOID, currentSessionKey) {
        var ajaxResult = $.ajax({
            type: "POST",
            url: domain + "/UKGetCertExtensions",
            async: false,
            dataType: "json",
            data: {
                "Cert": szCert,
                "OID": szOID,
                "SessionId": currentSessionKey
            },
            success: function (data) {
                //返回扩展项数据
            }
        });
        return ajaxResult.responseText;
    },
    //数据签名
    UKSignData: function (pszContainerName, inData, currentSessionKey) {
        var ajaxResult = $.ajax({
            type: 'POST',
            url: domain + "/UKSignData",
            async: false,
            data: {
                "ContainerName": pszContainerName,
                "InData": inData,
                "SessionId": currentSessionKey
            },
            success: function (data) {
                // jsondata.Data1;  返回证书数据
            }
        });
        return ajaxResult.responseText;
    },
    //附件签名
    UKSignFileData: function (pszContainerName, inData, currentSessionKey) {
        var ajaxResult = $.ajax({
            type: 'POST',
            url: domain + "/UKSignFileData",
            async: false,
            data: {
                "ContainerName": pszContainerName,
                "InData": inData,
                "SessionId": currentSessionKey
            },
            success: function (data) {
                // jsondata.Data1;  返回证书数据
            }
        });
        return ajaxResult.responseText;
    },
    //口令验证
    UKVerifyPin: function (szUserPin, currentSessionKey) {
        var ajaxResult = $.ajax({
            type: "POST",
            url: domain + "/UKVerifyPin",
            async: false,
            dataType: "json",
            data: {
                "szUserPin": szUserPin,
                "SessionId": currentSessionKey
            },
            success: function (data) {
                //返回成功标识
            }
        });
        return ajaxResult.responseText;
    },
    //验证签名
    UKVerifyData: function (signCert, signData, destdata, nCertFlag, currentSessionKey) {
        var ajaxResult = $.ajax({
            type: 'POST',
            url: domain + "/UKVerifyData",
            async: false,
            data: {
                "CertData": signCert,
                "SrcData": signData,
                "DestData": destdata,
                "nCertFlag": nCertFlag,
                "SessionId": currentSessionKey
            },
            success: function (data) {
                //返回成功标识
            }
        });
        return ajaxResult.responseText;
    },
    //附件验签
    UKVerifyFileData: function (signCert, signData, destdata, nCertFlag, currentSessionKey) {
        var ajaxResult = $.ajax({
            type: 'POST',
            url: domain + "/UKVerifyFileData",
            async: false,
            data: {
                "CertData": signCert,
                "SrcData": signData,
                "DestData": destdata,
                "nCertFlag": nCertFlag,
                "SessionId": currentSessionKey
            },
            success: function (data) {
                //返回成功标识
            }
        });
        return ajaxResult.responseText;
    },
    //导出SM2公钥
    UKExportSM2Key: function (dwCertFlag, pszContainerName, currentSessionKey) {
        var ajaxResult = $.ajax({
            type: "POST",
            url: domain + "/UKExportSM2Key",
            async: false,
            dataType: "json",
            data: { "Flag": dwCertFlag, "ContainerName": pszContainerName, "SessionId": currentSessionKey },
            success: function (data) {
                //返回证书数据
            }
        });
        return ajaxResult.responseText;
    },
    //导出RSA公钥
    UKExportRSAKey: function (dwCertFlag, pszContainerName, currentSessionKey) {
        var ajaxResult = $.ajax({
            type: "POST",
            url: domain + "/UKExportRSAKey",
            async: false,
            dataType: "json",
            data: { "Flag": dwCertFlag, "ContainerName": pszContainerName, "SessionId": currentSessionKey },
            success: function (data) {
                //返回证书数据
            }
        });
        return ajaxResult.responseText;
    }
}



function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg)) {
        return unescape(arr[2]);
    } else {
        return null;
    }
}

function setCookie(name, value) {
    var Days = 1;
    var exp = new Date();
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
}

function getRandSeesionKey() {
    var key = "";
    for (var i = 1; i <= 32; i++) {
        var n = Math.floor(Math.random() * 16.0).toString(16);
        key += n;
    }

    return key;
}

//删除cookie
function delCookie(name) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval = getCookie(name);
    if (cval != null) document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}

function JsonFlag(data) {
    if (data != 'undefined') {
        var jsondata = eval('(' + data + ')');
        if (jsondata.result == "true") {
            return true;
        } else {
            return false;
        }
    } else
        return false;
}


function JsonData(data) {
    if (data != 'undefined') {
        var jsondata = eval('(' + data + ')');
        if (jsondata.result == "true") {
            return jsondata.Data1;
        } else {
            return "";
        }
    } else
        return "";
}

//是否是IE浏览器
function isIE() {
    var msie = /msie/.test(navigator.userAgent.toLowerCase()) || /rv:\d+\.0\S+\s+like\sgecko/.test(navigator.userAgent.toLowerCase());

    if (msie) {
        // IE浏览器
        return true;
    } else {
        return false;
    }
}



//以下是应用集成时调用的接口
function Connect(ConnectName) {
    var Mes = false;
    try {
        if (isIE()) {
            Mes = WGUKey.ConnectUK(ConnectName);

        } else {
            var currentSessionKey = getCookie("SessionKey");
            if (currentSessionKey == "" || currentSessionKey == null) {
                currentSessionKey = getRandSeesionKey();
                setCookie("SessionKey", currentSessionKey);
            }
            var b = WallGreatUKey.ConnectUK(ConnectName, currentSessionKey);
            var jsonB = eval('(' + b + ')');
            if (jsonB.result == "true") {
                Mes = true;
            }
        }
    }
    catch (err) {
        //       alert("连接SSKEY异常");
        return "err";
    }
    return Mes;
}


/**
* 证书解析
*/
function GetCertInfo(CertFlag, DataFlag, ContainerName) {
    if (isIE()) {
        var res = WGUKey.UKGetUkeyCertInfo(CertFlag, DataFlag, ContainerName);
        if (res == true) {
            return WGUKey.Data1;
        }
    } else {
        var currentSessionKey = getCookie("SessionKey");
        if (currentSessionKey == "" || currentSessionKey == null) {
            currentSessionKey = getRandSeesionKey();
            setCookie("SessionKey", currentSessionKey);
        }
        var b = WallGreatUKey.UKGetUkeyCertInfo(CertFlag, DataFlag, ContainerName, currentSessionKey);
        var jsonB = eval('(' + b + ')');
        if (jsonB.result == "true") {
            return utf8to16(base64decode(jsonB.Data1));
        }
    }
    return false;
}

/**
* 读取证书 dwCertFlag 1 签名 2 加密
*/
function ReadCertData(CertFlag, ContainerName) {
    if (isIE()) {
        var res = WGUKey.UKReadCertData(CertFlag, ContainerName);
        if (res == true) {
            return WGUKey.Data1;
        }
    } else {
        var currentSessionKey = getCookie("SessionKey");
        if (currentSessionKey == "" || currentSessionKey == null) {
            currentSessionKey = getRandSeesionKey();
            setCookie("SessionKey", currentSessionKey);
        }
        var b = WallGreatUKey.UKReadCertData(CertFlag, ContainerName, currentSessionKey);
        var jsonB = eval('(' + b + ')');
        if (jsonB.result == "true") {
            return jsonB.Data1;
        }
    }
    return false;
}


/**
* 签名
*/
function SignData(ContainerName, InData) {
    var Mes = false;
    if (isIE()) {
        Mes = WGUKey.UKSignData(ContainerName, InData);
        if (Mes == true)
            return WGUKey.Data1;

    } else {
        var currentSessionKey = getCookie("SessionKey");
        if (currentSessionKey == "" || currentSessionKey == null) {
            currentSessionKey = getRandSeesionKey();
            setCookie("SessionKey", currentSessionKey);
        }
        InData = base64encode(utf16to8(InData));

        var b = WallGreatUKey.UKSignData(ContainerName, InData, currentSessionKey);
        var jsonB = eval('(' + b + ')');
        if (jsonB.result == "true") {
            return jsonB.Data1;
        }
    }
    return false;
}

/**
* 验证签名
*/
function VerifyData(SignCert, InData, SignData) {
    var Mes = false;
    if (isIE()) {
        Mes = WGUKey.UKVerifyData(SignCert, InData, SignData, 0);
    } else {
        var currentSessionKey = getCookie("SessionKey");
        if (currentSessionKey == "" || currentSessionKey == null) {
            currentSessionKey = getRandSeesionKey();
            setCookie("SessionKey", currentSessionKey);
        }
        InData = base64encode(utf16to8(InData));

        var b = WallGreatUKey.UKVerifyData(SignCert, InData, SignData, 0, currentSessionKey);
        var jsonB = eval('(' + b + ')');
        if (jsonB.result == "true") {
            Mes = true;
        }
    }
    return Mes;
}

/**
* 附件签名
*/
function SignFile(ContainerName, FilePath) {
    var Mes = false;
    if (isIE()) {
        Mes = WGUKey.UKSignFileData(ContainerName, FilePath);
        if (Mes == true)
            return WGUKey.Data1;

    } else {
        var currentSessionKey = getCookie("SessionKey");
        if (currentSessionKey == "" || currentSessionKey == null) {
            currentSessionKey = getRandSeesionKey();
            setCookie("SessionKey", currentSessionKey);
        }
        FilePath = base64encode(utf16to8(FilePath));

        var b = WallGreatUKey.UKSignFileData(ContainerName, FilePath, currentSessionKey);
        var jsonB = eval('(' + b + ')');
        if (jsonB.result == "true") {
            return jsonB.Data1;
        }
    }
    return false;
}
/**
* 附件验签
*/
function VerifyFile(SignCert, FilePath, SignData) {
    var Mes = false;
    if (isIE()) {
        Mes = WGUKey.UKVerifyFileData(SignCert, FilePath, SignData, 0);
    } else {
        var currentSessionKey = getCookie("SessionKey");
        if (currentSessionKey == "" || currentSessionKey == null) {
            currentSessionKey = getRandSeesionKey();
            setCookie("SessionKey", currentSessionKey);
        }
        FilePath = base64encode(utf16to8(FilePath));

        var b = WallGreatUKey.UKVerifyFileData(SignCert, FilePath, SignData, 0, currentSessionKey);
        var jsonB = eval('(' + b + ')');
        if (jsonB.result == "true") {
            Mes = true;
        }
    }
    return Mes;
}


/**
*导SM2公钥
**/
function ExportSM2Key(CertFlag, ContainerName) {
    if (isIE()) {
        var res = WGUKey.UKExportSM2Key(CertFlag, ContainerName);
        if (res == true) {
            return WGUKey.Data1;
        }
    } else {
        var currentSessionKey = getCookie("SessionKey");
        if (currentSessionKey == "" || currentSessionKey == null) {
            currentSessionKey = getRandSeesionKey();
            setCookie("SessionKey", currentSessionKey);
        }
        var b = WallGreatUKey.UKExportSM2Key(CertFlag, ContainerName, currentSessionKey);
        var jsonB = eval('(' + b + ')');
        if (jsonB.result == "true") {
            return jsonB.Data1;
        }
    }
    return false;
}


/**
*导RSA公钥
**/
function ExportRSAKey(CertFlag, ContainerName) {
    if (isIE()) {
        var res = WGUKey.UKExportRSAKey(CertFlag, ContainerName);
        if (res == true) {
            return WGUKey.Data1;
        }
    } else {
        var currentSessionKey = getCookie("SessionKey");
        if (currentSessionKey == "" || currentSessionKey == null) {
            currentSessionKey = getRandSeesionKey();
            setCookie("SessionKey", currentSessionKey);
        }
        var b = WallGreatUKey.UKExportRSAKey(CertFlag, ContainerName, currentSessionKey);
        var jsonB = eval('(' + b + ')');
        if (jsonB.result == "true") {
            return jsonB.Data1;
        }
    }
    return false;
}


/**
* 证书解析扩展项
*/
function GetCertExtensions(CertData, ExtnsID) {
    if (isIE()) {
        var res = WGUKey.UKGetCertExtensions(CertData, ExtnsID);
        if (res == true) {
            return WGUKey.Data1;
        }
    } else {
        var currentSessionKey = getCookie("SessionKey");
        if (currentSessionKey == "" || currentSessionKey == null) {
            currentSessionKey = getRandSeesionKey();
            setCookie("SessionKey", currentSessionKey);
        }
        var b = WallGreatUKey.UKGetCertExtensions(CertData, ExtnsID, currentSessionKey);
        var jsonB = eval('(' + b + ')');
        if (jsonB.result == "true") {
            return utf8to16(base64decode(jsonB.Data1));
        }
    }
    return false;
}
/**
* 验证口令
*/
function verifyUPin(szPin) {
    var Mes = false;
    if (isIE()) {
        Mes = WGUKey.UKVerifyPin(szPin);
        if (Mes == false) {
            Mes = WGUKey.Data1;
        }

    } else {
        var currentSessionKey = getCookie("SessionKey");
        if (currentSessionKey == "" || currentSessionKey == null) {
            currentSessionKey = getRandSeesionKey();
            setCookie("SessionKey", currentSessionKey);
        }
        var b = WallGreatUKey.UKVerifyPin(szPin, currentSessionKey);
        var jsonB = eval('(' + b + ')');
        if (jsonB.result == "true") {
            Mes = true;
        } else {
            Mes = utf8to16(base64decode(jsonB.Data1));
        }
    }
    return Mes;
	}