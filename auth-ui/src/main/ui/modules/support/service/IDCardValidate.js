/**
 * Created by y_zhang.neu on 2015/9/28.
 * validate ID Card Number
 */
'use strict';
angular.module('Validateform', [])
    .factory('IDCard', [function () {
        var factory = {};
        /**
         * 校验身份证是否合法
         * @param number
         * {isvalid:(true/false),message:'错误信息'}
         */
        factory.validateIDCard = function (number) {
            //检查号码是否符合规范，包括长度，类型
            function isCardNo(card) {
                //身份证号码为18位,前17位为数字，最后一位是校验位，可能为数字或字符X
                var reg = /(^\d{17}(\d|X)$)/;
                if (reg.test(card) === false) {
                    return false;
                }
                return true;
            }

            //检查生日是否正确
            function checkBirthday(card) {
                var len = card.length;
                //身份证18位时，次序为省（3位）市（3位）年（4位）月（2位）日（2位）校验位（4位），校验位末尾可能为X
                if (len === 18) {
                    var eighteen = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/;
                    var arrdata = card.match(eighteen);
                    var year = arrdata[2];
                    var month = arrdata[3];
                    var day = arrdata[4];
                    var birthday = new Date(year + '/' + month + '/' + day);
                    return verifyBirthday(year, month, day, birthday);
                }
                return false;
            }

            //校验日期
            function verifyBirthday(year, month, day, birthday) {
                var now = new Date();
                var nowyear = now.getFullYear();
                //年月日是否合理
                if (birthday.getFullYear() === parseInt(year, 10) && (birthday.getMonth() + 1) === parseInt(month, 10) && birthday.getDate() === parseInt(day, 10)) {
                    //判断年份的范围（1岁到100岁之间) 南宁制卡要求
                    var time = nowyear - year;
                    if (time >= 1 && time <= 100) {
                        return true;
                    }
                    return false;
                }
                return false;
            }

            //判断校验位是否正确
            function checkParity(card) {
                var len = card.length;
                if (len === 18) {
                    var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
                    var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
                    var cardTemp = 0, i, valnum;
                    for (i = 0; i < 17; i++) {
                        cardTemp += card.substr(i, 1) * arrInt[i];
                    }
                    valnum = arrCh[cardTemp % 11];
                    if (valnum === card.substr(17, 1)) {
                        return true;
                    }
                    return false;
                }
                return false;
            }

            var IDCardValidateResult = {isValid: 'true', message: ''};
            //校验身份证位数是否为18位
            if (number.length !== 18) {
                IDCardValidateResult = {isValid: 'false', message: '录入证件号码位数不正确,应为18位'};
                return IDCardValidateResult;
            }
            if (!isCardNo(number)) {
                IDCardValidateResult = {isValid: 'false', message: '身份证格式不正确'};
                return IDCardValidateResult;
            }
            if (!checkBirthday(number)) {
                IDCardValidateResult = {isValid: 'false', message: '身份证出生日期不正确'};
                return IDCardValidateResult;
            }
            if (!checkParity(number)) {
                IDCardValidateResult = {isValid: 'false', message: '身份证号码有误,请修改'};
                return IDCardValidateResult;
            }
            return IDCardValidateResult;
        };
        /**
         * 获取出生日期
         * @param number
         */
        factory.getBirthday = function (number) {
            var birthYear = number.substring(6, 10);
            var birthMonth = number.substring(10, 12);
            var birthDay = number.substring(12, 14);
            var birthDate = new Date(birthYear, parseInt(birthMonth, 10) - 1, birthDay);
            return birthDate;
        };
        /**
         * 获取性别
         * @param number
         */
        factory.getSex = function (number) {
            var sex = number.substring(16, 17);
            if ((parseInt(sex, 10) % 2) === 0) {
                return '2';
            } else {
                return '1';
            }
        };
        return factory;
    }]);