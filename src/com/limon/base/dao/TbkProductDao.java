package com.limon.base.dao;

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
public interface TbkProductDao {
	public void saveTbkProduct(Map<String,Object> map);
	public void saveTbkProductObj(TbkProduct tu);
	public void saveTbkProductTop100Obj(TbkProduct tu);
	public void saveTbkProductPaoliangObj(TbkProduct tu);
	public void saveTbkProductList(List<TbkProduct> list);
	public void saveTbkGroupSend(Integer tpid);
	public void updateTbkProductDate(TbkProduct tp);
	public void updateTbkProductTkl(TbkProduct tp);
	public void updateTbkProductTg(TbkProduct tp);
	public void updateTbkProductShow(String typename);
	public void updateTbkProductSend(String id);
	public void updateTbkProductJh(Map<String, Object> map);
	public void updateTbkProductTy(Map<String, Object> map);
	public void updateTbkProductQqhd(Map<String, Object> map);
	public void updateTbkProduct(Map<String,Object> map);
	public void deleteTbkProductByDtkid(String dtkid);
	public void deleteTbkProductOutTime();
	public void deleteTbkProductAll();
	public void deleteTbkProductTop100All();
	public void deleteTbkProductPaoliangAll();
	public void deleteTbkProductByDate(String date);
	public void deleteTbkProductLast7Date(String date);
	public void deleteTbkProductByType(Map<String,Object> map);
	public List<TbkProduct> getTbkProductListAll();
	public List<TbkProduct> getTbkProductListNoTkl();
	public List<TbkProduct> getTbkProductList(Map<String,Object> map);
	public Integer getTbkProductListCount(Map<String,Object> map);
	public TbkProduct getTbkProductByPid(Map<String,Object> map);
	public List<Group> getGroupByPidAndZd(Integer pid);
	public List<Group> getGroupByPidAndNoZd(Integer pid);
	public List<Group> getAllGroupList();
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
	public List<Group> getGroupByPid(Integer pid);
	public Group getGroupByName(String name);
	public Group getGroupById(Integer id);
	public List<TbkProduct> getTbkProductByNiId(String pid);
	public TbkProduct searchTbkProduct(Map<String,Object> map);
	public Group getGroupByNameAndUid(Map<String,Object> map);
	public List<Group> getGroupByWxName(String name);
	public TbkProduct getTbkProductSelect(Map<String,Object> map);
	public Integer getTbkProductSelectCount(Integer uid);
	public TbkProduct getTbkProductTop100(Integer start);
	public TbkProduct getTbkProductPaoliang(Integer start);
	public TbkProduct getTbkProductTotal(String start);
	public List<TbkProduct> getTbkProductListNoTg();
	
	public void saveTbkSr(TbkSr sr);
	public void updateTbkSr(TbkSr sr);
	public List<TbkSr> getTbkSrByOrderno(Map<String,Object> map);
	public List<TbkSr> getTbkSrByOrdernoNoFee(Map<String,Object> map);
	public List<TbkSr> getTbkSrListByTime(Map<String,Object> map);
	
	public void saveTbkPoint(TbkPoint tp);
	public void updateTbkPoint(TbkPoint tp);
	public TbkPoint getTbkPointByDateAndWxname(TbkPoint tp);
	public TbkPoint getTbkPointByTimeAndWxname(Map<String,Object> map);
	
	public void saveTbkDaySend(Map<String,Object> map);
	public List<TbkDaySend> getTbkDaySend(Map<String,Object> map);
	public Integer getTbkDaySendCount(Map<String,Object> map);
	public Integer getTbkDaySendByPid(Integer pid);
	
	public void updateTbkGroup(Map<String,Object> map);
	public void updateTbkGroupYg(Map<String,Object> map);
	public void insertTbkAccountRecord(Map<String,Object> map);
	public Integer getTbkAccountRecord(Map<String,Object> map);
	public TbkProduct getTlmSearch(Map<String,Object> map);
	public void saveTlmSearch(TbkProduct tp);
	public Integer getTbkProductCountByType(String typename);
}
