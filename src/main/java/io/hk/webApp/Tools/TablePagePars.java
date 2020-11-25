package io.hk.webApp.Tools;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import io.framecore.Frame.PagePars;

/**
 * 分页
 * @author Administrator
 *
 */
public class TablePagePars extends PagePars {


    public TablePagePars()
	{
		Pars = new Hashtable<String, Object>();
	}
	
	public TablePagePars(HttpServletRequest request) {
		Pars = new Hashtable<String, Object>();

		//Integer offset = 0;

		if (request.getParameter("pageSize") != null) {
			PageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		if (request.getParameter("pageIndex") != null) {
			PageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		}		 
		if (request.getParameter("order") != null) {
			Order = request.getParameter("order");
		}

		Enumeration<String> eles = request.getParameterNames();
		while (eles.hasMoreElements()) {

			String str = eles.nextElement();
			if (str.equals("pageSize") || str.equals("offset")
					|| str.equals("order") || str.equals("pageIndex")) {
				continue;
			}
			if(request.getParameter(str)==null || request.getParameter(str).isEmpty())
			{
				continue;
			}

			Pars.put(str, request.getParameter(str));
			
			
		}
	}

}
