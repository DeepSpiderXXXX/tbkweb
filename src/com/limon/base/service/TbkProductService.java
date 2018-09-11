package com.limon.base.service;

import java.util.List;
import java.util.Map;

import com.limon.base.model.TbkBanner;
import com.limon.base.model.TbkDaySend;
import com.limon.base.model.TbkPoint;
import com.limon.base.model.TbkProduct;
import com.limon.base.model.TbkSr;
import com.limon.base.model.TbkType;
import com.limon.base.model.TypeNum;
import com.limon.base.taobao.Group;


/**
 * @author gqf
 *
 * 2015-2-10 上午10:32:56
 */
public interface TbkProductService {
	public void saveTbkProduct(Map<String,Object> map);
	public void saveTbkProductTop100Obj(TbkProduct tu);
	public void saveTbkProductPaoliangObj(TbkProduct tu);
	public void saveTbkProductObj(TbkProduct tu);
	public void updateTbkProductShow(String typename);
	public void updateTbkProductSend(String id);
	public void updateTbkProductJh(String id,String srbl);
	public void updateTbkProductTy(String id,String srbl);
	public void updateTbkProductQqhd(String id,String srbl);
	public void saveTbkProductList(List<TbkProduct> tlist);
	public void saveTbkGroupSend(Integer tpid);
	public void updateTbkProduct(Map<String,Object> map);
	public void updateTbkProductDate(TbkProduct tp);
	public void updateTbkProductTkl(TbkProduct tp);
	public void deleteTbkProductByDate(String date);
	public void deleteTbkProductOutTime();
	public void deleteTbkProductByDtkid(String dtkid);
	public void deleteTbkProductLast7Date(String date);
	public void deleteTbkProductByType(String type);
	public void deleteTbkProductTop100All();
	public void deleteTbkProductPaoliangAll();
	public void deleteTbkProductAll();
	public List<TbkProduct> getTbkProductList(Map<String,Object> map);
	public TbkProduct searchTbkProduct(String searchkey,Integer start);
	public List<TbkProduct> getTbkProductListAll();
	public List<TbkProduct> getTbkProductListNoTkl();
	public void updateTbkProductTg(TbkProduct tp);
	public List<TbkProduct> getTbkProductListNoTg();
	public Integer getTbkProductListCount(Map<String,Object> map);
	public TbkProduct getTbkProductByPid(Map<String,Object> map);
	public TbkProduct getTbkProductTop100ByPid(Map<String,Object> map);
	public TbkProduct getTbkProductPaoliangByPid(Map<String,Object> map);
	public void updateTbkProductPoint(Integer id);
	public List<TbkProduct> getTbkProductListPoint3(Map<String,Object> map);
	public TbkProduct getTbkProductById(Integer id);
	public void updateTbkBanner(Map<String,Object> map);
	public void updateTbkBannerStatus(Map<String,Object> map);
	public List<TbkBanner> getBannerList();
	public List<TbkBanner> getBannerListAll();
	public void updateTbkProductPointRandom(Map<String,Object> map);
	public List<TypeNum> getTypeNum();
	public TbkType getType(Map<String,Object> map);
	public List<TbkType> getTypeList(); 
	public Group getGroupByName(String name);
	public Group getGroupByWxName(String name);
	public Group getGroupByNameAndUid(String name,Integer uid);
	public Group getGroupById(Integer id);
	public List<Group> getGroupByPid(Integer pid);
	public List<Group> getAllGroupList();
	public List<Group> getGroupByPidAndZd(Integer pid);
	public List<Group> getGroupByPidAndNoZd(Integer pid);
	public TbkProduct getTbkProductSelect(Integer uid,Integer start);
	public Integer getTbkProductSelectCount(Integer uid);
	public TbkProduct getTbkProductTop100(Integer start);
	public TbkProduct getTbkProductPaoliang(Integer start);
	public TbkProduct getTbkProductTotal(String start);
	public TbkProduct getTbkProductByNiId(String pid);
	
	public TbkProduct getTlmSearch(String pid,Integer gid,String createdate);
	public void saveTlmSearch(TbkProduct tp);
	
	/*类别工具类*/
	public String getSiteType(String taobaoType);
	public Integer getSiteSort(String taobaoType);
	public boolean isImport(String taobaoType,Double yhbl,Double srbl,Double yj,Integer salenum,Integer leftcnum,String ccontent);
	
	public void saveTbkSr(TbkSr sr);
	public void updateTbkSr(TbkSr sr);
	public List<TbkSr> getTbkSrByOrderno(String orderno,String pid,String fee);
	public List<TbkSr> getTbkSrByOrdernoNoFee(String orderno,String pid);
	public List<TbkSr> getTbkSrListByTime(Map<String,Object> map);
	
	public void saveTbkPoint(TbkPoint tp);
	public void updateTbkPoint(TbkPoint tp);
	public TbkPoint getTbkPointByDateAndWxname(TbkPoint tp);
	public TbkPoint getTbkPointByTimeAndWxname(Map<String,Object> map);
	
	public void saveTbkDaySend(Map<String,Object> map);
	public List<TbkDaySend> getTbkDaySend(Map<String,Object> map);
	public Integer getTbkDaySendCount(Map<String,Object> map);
	public Integer getTbkDaySendByPid(Integer pid);
	
	public void updateTbkGroup(String fee,String wxname);
	public void updateTbkGroupYg(String fee,String wxname);
	public void insertTbkAccountRecord(Map<String,Object> map);
	public Integer getTbkAccountRecord(Map<String,Object> map);
	
	public Integer getTbkProductCountByType(String typename);
}