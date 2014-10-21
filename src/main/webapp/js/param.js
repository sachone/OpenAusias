/*
 * Copyright (C) July 2014 Rafael Aznar
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

var param = function() {
    return {
        getUrlObjectFromUrlString: function(url) {
            if (typeof url == 'undefined') {
                return {};
            } else {
                if (url == "") {
                    return {};
                } else {
                    a = url.split('&');
                }
            }
            var b = {};
            for (var i = 0; i < a.length; ++i)
            {
                var p = a[i].split('=');
                if (p.length != 2)
                    p = ['id', p[0]]; //id parameter by default
                b[p[0]] = decodeURIComponent(p[1].replace(/\+/g, " "));
            }
            return b;
        }
        ,
        getUrlObjectFromParamsWithoutParamArray: function(urlObj, nameParameterArray) {
            var newUrlObj = jQuery.extend(true, {}, urlObj); //http://stackoverflow.com/questions/122102/what-is-the-most-efficient-way-to-clone-an-object
            $.each(nameParameterArray, function() {
                delete newUrlObj[this];
            })
            return newUrlObj;
        },
        getUrlStringFromParamsObject: function(urlObj) {
            var result = "";
            for (var key in urlObj) {
                result += "&" + key + "=" + urlObj[key];
            }
            return result.substring(1);
        },
        validateUrlObjectParameters: function(objParams) {
            //security borders comprobation, pendent of moving
            if (objParams["vf"] > 20) {
                objParams["vf"] = 20;
            }
            if (objParams["page"] > 100000) {
                objParams["page"] = 100000;
            }
            if (objParams["rpp"] > 50) {
                objParams["rpp"] = 50;
            }
            return objParams;
        },
        defaultizeUrlObjectParameters: function(objParams) {
            if (typeof objParams["page"] == 'undefined')
                objParams["page"] = 1;
            if (typeof objParams["id"] == 'undefined')
                objParams["id"] = 1;
            if (typeof objParams["rpp"] == 'undefined')
                objParams["rpp"] = 10;
            if (typeof objParams["vf"] == 'undefined')
                objParams["vf"] = 10;
            return objParams;
        }

    }
}










