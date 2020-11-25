package io.hk.webApp.DataAccess;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

 
import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.PhoneMsg;
 
 
 

@Repository(value="phoneMsgSet")
@Scope(value="prototype")
public class PhoneMsgSet extends Set<PhoneMsg> {

	public PhoneMsgSet() {
		super(_db.getDbName());
	}

	@Override
	public Class<PhoneMsg> getType() {
		return PhoneMsg.class;
	}
	
	public void addValid(String phone,String validCode)
	{
		String format="[XXX],您的验证码是：{validCode}";
		
		
		PhoneMsg msg = new PhoneMsg();
		
		msg.setMessage(format.replace("{validCode}", validCode));
		msg.setPhone(phone);
		msg.setStatus(0);
		
		this.Add(msg);
		
		
	}
	

	

}
