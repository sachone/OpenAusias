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
package net.daw.helper;

import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

public class parameterCooker {

    public static String prepareMode(HttpServletRequest request) {
        String result = null;
        if (request.getParameter("mode") == null) {
            result = "wrappered";
        } else {
            result = request.getParameter("mode");
        }
        return result;
    }

    public static String prepareObject(HttpServletRequest request) {
        String result = null;
        if (request.getParameter("ob") == null) {
            result = "usuario";
        } else {
            result = request.getParameter("ob");
        }
        return result;
    }

    public static String prepareCamelCaseObject(HttpServletRequest request) {
        String result = null;
        if (request.getParameter("ob") == null) {
            result = "Usuario";
        } else {
            result = Character.toUpperCase(request.getParameter("ob").charAt(0)) + request.getParameter("ob").substring(1);
        }
        return result;
    }

    public static int prepareId(HttpServletRequest request) {
        int result = 0;
        if (request.getParameter("id") == null) {
            result = 0;
        } else {
            result = Integer.parseInt(request.getParameter("id"));
        }
        return result;
    }

    public static String prepareJson(HttpServletRequest request) {
        String result = null;
        if (request.getParameter("json") == null) {
            result = "";
        } else {
            result = request.getParameter("json").replaceAll("%2F", "/");
        }
        return result;
    }

    public static String prepareOperation(HttpServletRequest request) {
        String result = null;
        if (request.getParameter("op") == null) {
            result = "inicio";
        } else {
            result = request.getParameter("op");
        }
        return result;
    }

    public static Integer prepareRpp(HttpServletRequest request) {
        int intRegsPerPag;
        if (request.getParameter("rpp") == null) {
            intRegsPerPag = 10;
        } else {
            intRegsPerPag = Integer.parseInt(request.getParameter("rpp"));
        }
        return intRegsPerPag;
    }

    public static Integer preparePage(HttpServletRequest request) {
        int intPage;
        if (request.getParameter("page") == null) {
            intPage = 1;
        } else {
            intPage = Integer.parseInt(request.getParameter("page"));
        }
        return intPage;
    }

    public static ArrayList<FilterBeanHelper> prepareFilter(HttpServletRequest request) {
        ArrayList<FilterBeanHelper> alFilter = new ArrayList<>();
        if (request.getParameter("filter") != null) {
            if (request.getParameter("filteroperator") != null) {
                if (request.getParameter("filtervalue") != null) {
                    FilterBeanHelper oFilterBean = new FilterBeanHelper();
                    oFilterBean.setFilter(request.getParameter("filter"));
                    oFilterBean.setFilterOperator(request.getParameter("filteroperator"));
                    oFilterBean.setFilterValue(request.getParameter("filtervalue"));
                    oFilterBean.setFilterOrigin("user");
                    alFilter.add(oFilterBean);
                }
            }
        }
        if (request.getParameter("systemfilter") != null) {
            if (request.getParameter("systemfilteroperator") != null) {
                if (request.getParameter("systemfiltervalue") != null) {
                    FilterBeanHelper oFilterBean = new FilterBeanHelper();
                    oFilterBean.setFilter(request.getParameter("systemfilter"));
                    oFilterBean.setFilterOperator(request.getParameter("systemfilteroperator"));
                    oFilterBean.setFilterValue(request.getParameter("systemfiltervalue"));
                    oFilterBean.setFilterOrigin("system");
                    alFilter.add(oFilterBean);
                }
            }
        }
        return alFilter;
    }

    public static HashMap<String, String> prepareOrder(HttpServletRequest request) {
        HashMap<String, String> hmOrder = new HashMap<>();
        if (request.getParameter("order") != null) {
            if (request.getParameter("ordervalue") != null) {
                hmOrder.put(request.getParameter("order"), request.getParameter("ordervalue"));
            } else {
                hmOrder = null;
            }
        } else {
            hmOrder = null;
        }
        return hmOrder;
    }
}
