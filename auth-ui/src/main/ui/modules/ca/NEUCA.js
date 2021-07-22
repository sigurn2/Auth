var $NEUCA = {
    GX_API: (function () {
        /**
         * 初始化
         * @param succFn 成功回调
         * @param failFn 出参: {errorCode:"0",errorMsg:"错误中文信息"},errorCode=0 未安装驱动，errorCode=1 未插入u key
         */

        var CurrentObj;

        function initCAObject(succFn, failFn) {
            CurrentObj = CreateXTXAppObject();
            // alert(CurrentObj)
            if (CurrentObj) {
                if (succFn) {
                    succFn();
                }
                return;
            }

            CurrentObj = CreateWebSocketObject(succFn, failFn);
            if (!CurrentObj && failFn) {
                failFn();
            }

        }


        /**
         * 加载证书
         * @param succFn 出参 证书数组[]
         * @param failFn 出参: {errorCode:"0",errorMsg:"错误中文信息"}
         */
        function loadCert(succFn, failFn) {
            CurrentObj.SOF_GetUserList(function (data) {
                var ret = data.retVal;
                if (!ret) {
                    succFn([]);
                    return;
                }

                var retArray = [];
                var cert_array = ret.split("&&&");
                for (var i = 0; i < cert_array.length; i++) {
                    var content = cert_array[i];
                    if (!content) {
                        continue;
                    }

                    cert_info = content.split("||");
                    if (cert_info.length < 2) {
                        continue;
                    }

                    var obj = {};
                    obj.certid = cert_info[1];
                    obj.certcn = cert_info[0];
                    retArray.push(obj);
                }

                if (retArray.length == 0) {
                    succFn(retArray);
                    return;
                }

                var cert_number = retArray.length;
                var retCertArray = [];
                for (var i = 0; i < retArray.length; i++) {
                    CurrentObj.SOF_ExportUserCert(retArray[i].certid, function (data) {
                        var cert_content = data.retVal;
                        if (cert_content) {
                            CurrentObj.SOF_GetCertInfo(cert_content, 8, function (data) {
                                var issuer_cn = data.retVal;
                                if (issuer_cn == "Guangxi SM2 CA" || issuer_cn == "Guangxi CA") {
                                    retCertArray.push(data.ctx);
                                }

                                if (--cert_number == 0) {
                                    succFn(retCertArray);
                                    return;
                                }
                            }, data.ctx);
                        } else {
                            if (--cert_number == 0) {
                                succFn(retCertArray);
                                return;
                            }
                        }
                    }, retArray[i]);
                }
            });
        }


        /**
         * 加载单位编号
         * @param succFn 出参 单位编号数组[]
         * @param failFn 出参: {errorCode:"0",errorMsg:"错误中文信息"}
         */
        function loadNumber(certID, succFn, failFn) {
            CurrentObj.SOF_ExportUserCert(certID, function (data) {
                var cert = data.retVal;
                if (!cert) {
                    if (failFn) {
                        var obj = {};
                        obj.errorCode = "1";
                        obj.errorMsg = "获取证书失败";
                        failFn(obj);
                        return;
                    }
                }

                CurrentObj.SOF_GetCertInfoByOid(cert, "1.2.156.112562.6.1.2", function (data) {
                    var ret = data.retVal;
                    if (ret) {
                        succFn(ret.split("&"));
                        return;
                    }


                    CurrentObj.SOF_GetCertInfo(cert, 35, function (data) {
                        var ret = data.retVal;
                        if (!ret) {
                            if (failFn) {
                                var obj = {};
                                obj.errorCode = "2";
                                obj.errorMsg = "获取证书dn失败";
                                failFn(obj);
                                return;
                            }


                        }

                        if (!succFn) {
                            return;
                        }

                        var key_value_list = ret.split(",");
                        for (var i = 0; i < key_value_list.length; i++) {
                            var key_value = key_value_list[i].split("=");
                            if (key_value.length < 2) {
                                continue;
                            }

                            var key = key_value[0];
                            var value = key_value[1];
                            if (key == "surname" || key == "OU") {
                                succFn(value.split("&"));
                                return;
                            }
                        }

                        if (failFn) {
                            var obj = {};
                            obj.errorCode = "3";
                            obj.errorMsg = "解析证书dn失败";
                            failFn(obj);
                            return;
                        }

                    });

                })


            });
        }

        /**
         * 数据签名
         * @param data 原文
         * @param succFn 成功回调 出参签名后的值
         * @param failFn 失败回调 出参: {errorCode:"0",errorMsg:"错误中文信息"}
         */
        function signData(certID, data, succFn, failFn) {
            CurrentObj.SOF_SignData(certID, data, function (data) {
                var ret = data.retVal;
                if (ret) {
                    succFn(ret);
                    return;
                }
                failFn(ret);
            });
        }

        /**
         * 附件签名
         * @param filePath 文件path
         * @param succFn 成功回调 出参签名后的值
         * @param failFn 失败回调 出参: {errorCode:"0",errorMsg:"错误中文信息"}
         */
        function signFile(certID, filePath, succFn, failFn) {
            CurrentObj.SOF_SignFile(certID, filePath, function (data) {
                var ret = data.retVal;
                if (ret) {
                    succFn(ret);
                    return;
                }
                failFn(ret);
            });
        }

        /**
         * 获取用户用户证书（验证需要的cert）
         * @param certId 证书唯一标识
         * @param succFn 出参 用户证书
         * @param failFn 出参: {errorCode:"0",errorMsg:"错误中文信息"}
         */
        function exportUserCert(certId, succFn, failFn) {
            CurrentObj.SOF_ExportUserCert(certId, function (data) {
                var ret = data.retVal;
                if (ret) {
                    succFn(ret);
                    return;
                }
                failFn(ret);
            });
        }

        /**
         * 用户登录
         * @param certId 证书唯一标识
         * @param certId pin码
         * @param succFn 登录成功
         * @param failFn 登录失败 {errorCode:"0",errorMsg:"失败原因"}
         */
        function login(certId, pinPwd, succFn, failFn) {
            CurrentObj.SOF_Login(certId, pinPwd, function (data) {
                var ret = data.retVal;
                if (ret) {
                    succFn();
                } else {
                    CurrentObj.SOF_GetPinRetryCount(certId, function (data) {
                        var obj = {};
                        obj.errorCode = "1";
                        obj.errorMsg = "校验证书密码失败，密码剩余重试次数（" + data.retVal + "），请重新输入证书密码！";
                        if (failFn) {
                            failFn(obj);
                        }
                    });
                }
            });
        }

        return {
            initCAObject: initCAObject,
            loadCert: loadCert,
            loadNumber: loadNumber,
            signData: signData,
            signFile: signFile,
            exportUserCert: exportUserCert,
            login: login
        }
    })(),

    DF_API: (function () {

        /**
         * 初始化
         * @param succFn 成功回调
         * @param failFn 出参: {errorCode:"0",errorMsg:"错误中文信息"},errorCode=0 未安装驱动，errorCode=1 未插入u key
         */
        function initCAObject(succFn, failFn) {
            var retVal = false;
            var initErr = {};

            retVal = Connect("WALLGREAT");
            if (retVal === false) {
                initErr.errorCode = "0";
                initErr.errorMsg = "未插入uskey";
            } else if (retVal == "err") {
                initErr.errorCode = "1";
                initErr.errorMsg = "未安装驱动";
            } else {
                succFn();
                return;
            }

            failFn(initErr);
        }

        /**
         * 加载证书
         * @param succFn 出参 证书数组    [ {certid:"21313",certcn:"dasfaasfa"} ,{},.....]
         * @param failFn 出参: {errorCode:"0",errorMsg:"错误中文信息"}
         */
        function loadCert(succFn, failFn) {
            var certArr = []
            var certData = {};
            var res = GetCertInfo(1, 1, "WALLGREATSM2");
            var cid = GetCertInfo(1, 5, "WALLGREATSM2");

            if (res != false && cid != false) {
                certData.certid = cid;
                certData.certcn = res;
                certArr.push(certData);
                succFn(certArr);
                return;

            }
            else {
                var certErr = {};
                certErr.errorCode = "0";
                certErr.errorMsg = "请确认uskey中是否有证书";
                failFn(certErr);
            }

        }

        /**
         * 加载单位编号
         * @param certId 证书唯一标识
         * @param succFn 出参 单位编号ou数组["123","234","445",....]
         * @param failFn 出参: {errorCode:"0",errorMsg:"错误中文信息"}
         */
        function loadNumber(certId, succFn, failFn) {
            var numberData = [];

            var SM2szCertData = GetCertInfo(1, 18, "WALLGREATSM2");

            //如果是国密证书
            if (SM2szCertData == "sm3withsm2") {
                var certdata = getCertData("WALLGREATSM2");
                var res = GetCertExtensions(certdata, "1.34.56.876.3");

                //要求RSA证书更新过去的时候变成：20013336_20062546_20022222_20033333_20044444_20055555_20069666
                //res = "EmployeeID - 20013336&20062546&20022222&20033333&20044444&20055555&20069666";
                res = trim(res, "g");
                var arr = res.split("_");
                if (res != false) {
                    for (var i = 0; i < arr.length; i++) {
                        numberData.push(arr[i]);
                    }
                }
            } else {
                var certdata = getCertData("WALLGREAT");
                //RSA证书里面的OU字段，目前写入社保编号。
                var res = GetCertInfo(1, 12, "WALLGREAT");

                //res = "EmployeeID - 20013336&20062546&20022222&20033333&20044444&20055555&20069666";
                res = trim(res, "g");
                var arr = res.split("-");

                //SM2证书首先去掉EmployeeID -
                if (arr.length == 2) {
                    res = arr[1];
                } else {
                    res = false;
                }

                if (res != false) {
                    arr = res.split("&");
                    for (var i = 0; i < arr.length; i++) {
                        numberData.push(arr[i]);
                    }
                }
            }


            if (numberData.length > 0) {
                succFn(numberData);
            } else {
                var numberErr = {};
                numberErr.errorCode = "0";
                numberErr.errorMsg = "请确认uskey中是否有证书";
                failFn(numberErr)
            }
        }

        /**
         * 数据签名
         * @param certId 证书唯一标识
         * @param data 原文
         * @param succFn 成功回调 出参签名后的值
         * @param failFn 失败回调 出参: {errorCode:"0",errorMsg:"错误中文信息"}
         */
        function signData(certId, data, succFn, failFn) {
            var signErr = {};

            var SM2szCertData = GetCertInfo(1, 18, "WALLGREATSM2");

            if (data == "" || data == undefined) {
                signErr.errorCode = "0";
                signErr.errorMsg = "原文没有内容";
                failFn(signErr);
                return;
            } else {
                if (SM2szCertData == "sm3withsm2") {
                    var signData = SignData("WALLGREATSM2", data); //开始签名
                    if (signData == false) {
                        signErr.errorCode = "0";
                        signErr.errorMsg = "SSKey签名失败";
                        failFn(signErr);
                        return;
                    }

                    succFn(signData);
                    return;

                } else {
                    var RSAszCertData = getCertData("WALLGREAT");
                    if (RSAszCertData) {
                        var res = GetCertInfo(1, 0, "WALLGREAT");

                        var root = "DFCA Pubilc Root RSA";
                        if (res == root) {
                            var b = Connect("WALLGREATRSA");
                            var signData = SignData("WALLGREAT", data); //开始签名
                            if (signData == false) {
                                signErr.errorCode = "0";
                                signErr.errorMsg = "SSKey签名失败";
                                failFn(signErr);
                                return;
                            }

                            succFn(signData);
                            return;
                        } else {
                            signErr.errorCode = "0";
                            signErr.errorMsg = "该证书不是东方新诚信颁发的证书";
                            failFn(signErr);
                            return;
                        }
                    } else {
                        signErr.errorCode = "0";
                        signErr.errorMsg = "没有RSA证书";
                        failFn(signErr);
                        return;
                    }
                }

            }

        }

        /**
         * 附件签名
         * @param certId 证书唯一标识
         * @param filePath 文件path
         * @param succFn 成功回调 出参签名后的值
         * @param failFn 失败回调 出参: {errorCode:"0",errorMsg:"错误中文信息"}
         */
        function signFile(certId, filePath, succFn, failFn) {
            var signFileErr = {};

            var signFileData = "";
            var SM2szCertData = GetCertInfo(1, 18, "WALLGREATSM2");

            if (filePath == "" || filePath == undefined) {
                signFileErr.errorCode = "0";
                signFileErr.errorMsg = "文件路径没有内容";
                failFn(signFileErr);
                return;
            } else {
                if (SM2szCertData == "sm3withsm2") {
                    var signFileData = SignFile("WALLGREATSM2", filePath); //开始签名
                    if (signFileData == false) {
                        signFileErr.errorCode = "0";
                        signFileErr.errorMsg = "SSKey附件签名失败";
                        failFn(signFileErr);
                        return;
                    }

                    succFn(signFileData);
                    return;
                } else {
                    var b = Connect("WALLGREATRSA");
                    signFileData = SignFile("WALLGREAT", filePath); //开始签名
                    if (signFileData == false) {
                        signFileErr.errorCode = "0";
                        signFileErr.errorMsg = "SSKey附件签名失败";
                        failFn(signFileErr);
                        return;
                    }

                    succFn(signFileData);
                    return;
                }

            }

        }


        function exportUserCert(certId, succFn, failFn) {
            var keyErr = {};
            var keyData = "";
            var SM2szCertData = GetCertInfo(1, 18, "WALLGREATSM2");

            if (SM2szCertData == "sm3withsm2") {
                var ret = getCertData("WALLGREATSM2");
                if (ret === false) {
                    keyErr.errorCode = "0";
                    keyErr.errorMsg = "获取UKEY证书失败";
                    failFn(keyErr);
                    return;
                }
                keyData = ret;
                succFn(keyData);
                return;
            } else {
                var b = Connect("WALLGREATRSA");
                var ret = getCertData("WALLGREAT");
                if (ret === false) {
                    keyErr.errorCode = "0";
                    keyErr.errorMsg = "获取UKEY证书失败";
                    failFn(keyErr);
                    return;
                }
                keyData = ret;
                succFn(keyData);
                return;
            }

        }

        /**
         主动验证KEY  PIN 码  2018-10-12
         * @param userPin 输入的PIN码
         * @param succFn 成功回调 出参true
         * @param failFn 失败回调 出参: {errorCode:"0",errorMsg:"错误中文信息"}
         */
        function verifyUserPin(userPin, succFn, failFn) {
            var pinErr = {};
            if (userPin == "" || userPin == undefined) {
                pinErr.errorCode = "0";
                pinErr.errorMsg = "请输入用户PIN码";
                failFn(pinErr);
                return;
            } else {
                var isPin = verifyUPin(userPin);
                if (isPin === true) {
                    succFn();
                    return;
                } else {
                    pinErr.errorCode = "0";
                    pinErr.errorMsg = isPin;   //isPin验证错误信息
                    failFn(pinErr);
                    return;
                }
            }
        }

        /**
         * 用户登录
         * @param certId 证书唯一标识
         * @param certId pin码
         * @param succFn 登录成功
         * @param failFn 登录失败 {errorCode:"0",errorMsg:"失败原因"}
         */
        function login(certId, pinPwd, succFn, failFn) {
            verifyUserPin(pinPwd, succFn, failFn);
        }


        return {
            initCAObject: initCAObject,
            loadCert: loadCert,
            loadNumber: loadNumber,
            signData: signData,
            signFile: signFile,
            exportUserCert: exportUserCert,
            verifyUserPin: verifyUserPin,
            login: login
        }
    })()

};