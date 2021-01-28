package io.hk.webApp;

 
import io.framecore.Aop.Holder;
import io.framecore.Frame.JFrameException;
import io.framecore.Tool.HttpHelp;
import io.framecore.Tool.Md5Help;
import io.hk.webApp.DataAccess.*;
import io.hk.webApp.Domain.*;
import io.hk.webApp.Tools.BaseType;
import io.hk.webApp.Tools.BsonUtil;
import org.bson.Document;

import java.util.*;

public class Test {
	
	public static void main(String[] args)  {
		ProductSet product = Holder.getBean(ProductSet.class);
		product.ToList().forEach((a)->{
			a.setIllegal("1");
			product.Update(a.getId(),a);
		});
	}
	
	
 	public static void addProduct(){
		ProductSet productSet = Holder.getBean(ProductSet.class);
		for (int i = 1; i <= 100; i++) {
			Product product = new Product();
			product.setFactoryId("5fd0858b53423311bf0edbc8");
			product.setName("产品："+i);
			product.setBrandId("5fcd877cbcef2d219f3faa30");
			product.setCategoryCode("5fcd871cbcef2d219f3faa2e");
			String [] images = new String[]{
					"1607914072789-timg11.jpg",
					"1607914067646-timg.jfif",
					"1607928706251-logo-lb1.png",
					"1607928706129-logo_r.png"
			};

			product.setImage1(images[new Random().nextInt(4)]);
			product.setImage2(images[new Random().nextInt(4)]);
			product.setShield(BaseType.Status.YES.getCode());
			product.setTagHot("1");
			product.setTagRec("2");
			product.setUrlJd("京东外链");
			product.setUrlTaobao("淘宝外链");
			product.setPriceBegin1(10.0);
			product.setPriceEnd1(20.0);
			product.setPriceBegin2(30.0);
			product.setPriceEnd2(40.0);
			product.setSupplyPriceTax(50.0);
			product.setSupplyPriceTaxNo(60.0);
			product.setRetailPriceTax(70.0);
			product.setRetailPriceTaxNo(80.0);
			product.setPptImage("ppt展示图");
			product.setBaseDetail("基本信息");
			product.setSellingPoint("卖点");
			int status = new Random().nextInt(2)+1;
			product.setStatus(status+"");
			product.setDetail("商品详情");
			productSet.Add(product);
		}
	}
	
	public static void addDynamic(){
		DynamicSet dynamicSet = Holder.getBean(DynamicSet.class);
		UserSet userSet = Holder.getBean(UserSet.class);
		List<User> list = userSet.Where("type=?","1").ToList();
		for (int i = 1; i <= 100; i++) {
			Dynamic dynamic = new Dynamic();
			dynamic.setFactoryId(list.get(new Random().nextInt(list.size())).getId());
			dynamic.setDetails("动态："+i);
			String [] img = {"1607914072789-timg11.jpg","607914067646-timg.jfif","1607928706251-logo-lb1.png"};
			dynamic.setImg(img[new Random().nextInt(img.length)]);
			dynamicSet.Add(dynamic);
		}
	}

	public static void addUser(){
		UserSet userSet = Holder.getBean(UserSet.class);
		for (int i = 1; i <= 100; i++) {
			User user = new User();
			user.setName("供应商："+i);
			user.setPassword(Md5Help.toMD5("123"));
			user.setStatus("1");
			user.setPhone(UUID.randomUUID().toString().replace("-", ""));
			user.setType("1");
			user.setAddr("地址");
			user.setCompanyName("公司");
			user.setImg("1607569930681-timg.jfif");
			user.setTel("电话");
			user.setManage("范围");
			user.setProvince("省份");
			userSet.Add(user);
		}
	}
	
	
	//创建 factory
	public static void addFactory()
	{
		
		UserSet uset = Holder.getBean(UserSet.class);
		
		User user = new User();
		user.setIdentity(1);
		user.setPhone("15836465656");
		user.setPassword("123456");

		String uid = String.valueOf(uset.Add(user)) ;
		
		FactorySet set = Holder.getBean(FactorySet.class);
		
		Factory factory = new Factory();
		factory.setAddress("厦门一号路");
		factory.setAreaCode("1205");
		factory.setBusinessRank("电子数码");
		factory.setContactName("王总");
		factory.setContactPhone("15836465656");
		factory.setFatherFactoryId(null);
		factory.setHeadImg("aaa");
		factory.setName("厦门xx电子有限公司");
		factory.setUserId(uid);
		
		set.Add(factory);
		
	}

	public static void createScheme(){
		Scheme scheme = new Scheme();
		SchemeSet schemeSet = Holder.getBean(SchemeSet.class);
		scheme.setName("测试");
		Product product = new Product();
		SaleGoods saleGoods = new SaleGoods();
		product.setName("名");
		saleGoods.setName("二名");
		saleGoods.setProduct(BsonUtil.toDocument(product));
		saleGoods.setProductBean(product);
		List<Document> list = new ArrayList();
		list.add(BsonUtil.toDocument(saleGoods));
		scheme.setSalesGoods(list);
		schemeSet.Add(scheme);
	}
 
	
	//创建分类
	public static void addCatory()
	{
		CategorySet set = Holder.getBean(CategorySet.class);
		Category c1 =new Category();
		
		c1.setCode("dzsm");
		c1.setName("电子数码");
		
		set.Add(c1);
		
		Category c2 =new Category();
		
		c2.setCode("jydq");
		c2.setName("家用电器");
		
		set.Add(c2);
		
		
	}


	

}
