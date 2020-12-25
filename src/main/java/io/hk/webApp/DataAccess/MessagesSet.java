package io.hk.webApp.DataAccess;

import io.framecore.Frame.PageData;
import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.Messages;
import io.hk.webApp.Tools.TablePagePars;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository(value="MessagesSet")
@Scope(value="prototype")
public class MessagesSet extends Set<Messages> {

	public MessagesSet() {
		super(_db.getDbName());
	}

	@Override
	public Class<Messages> getType() {
		return Messages.class;
	}

	/**
	 * 初始化消息列表
	 * @param title
	 * @return
	 */
	public PageData<Messages> init(String title, String userId, TablePagePars pagePars) {

		List<Messages> list = this.Where("(title=?)and(receiveId=?)",title,userId).Limit(pagePars.PageSize,pagePars.PageIndex).OrderByDesc("time").ToList();
		long count = this.Where("(title=?)and(receiveId=?)",title,userId).Count();
		PageData<Messages> pageData = new PageData<>();
		pageData.total = count;
		pageData.rows = list;
		return pageData;
	}
}
