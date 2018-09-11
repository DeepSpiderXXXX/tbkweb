package com.limon.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.limon.base.dao.TbkProductDao;
import com.limon.base.model.TbkBanner;
import com.limon.base.model.TbkDaySend;
import com.limon.base.model.TbkPoint;
import com.limon.base.model.TbkProduct;
import com.limon.base.model.TbkSr;
import com.limon.base.model.TbkType;
import com.limon.base.model.TypeNum;
import com.limon.base.service.TbkProductService;
import com.limon.base.taobao.Group;
import com.limon.util.DateUtil;

/**
 * @author gqf
 *
 * 2015-2-10 上午10:32:56
 */
@Service("TbkProductService")
public class TbkProductServiceImpl implements TbkProductService {
	
	@Autowired
	private TbkProductDao tbkProductDao;

	@Override
	public Integer getTbkProductListCount(Map<String, Object> map) {
		return tbkProductDao.getTbkProductListCount(map);
	}

	@Override
	public List<TbkProduct> getTbkProductList(Map<String, Object> map) {
		return tbkProductDao.getTbkProductList(map);
	}

	@Override
	public void saveTbkProduct(Map<String, Object> map) {
		tbkProductDao.saveTbkProduct(map);
	}

	@Override
	public void updateTbkProduct(Map<String, Object> map) {
		tbkProductDao.updateTbkProduct(map);
	}

	@Override
	public void deleteTbkProductByType(String type) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("type", type);
		map.put("createdate", DateUtil.getToday());
		tbkProductDao.deleteTbkProductByType(map);
	}

	@Override
	public void updateTbkProductPoint(Integer id) {
		tbkProductDao.updateTbkProductPoint(id);
	}

	@Override
	public TbkProduct getTbkProductByPid(Map<String, Object> map) {
		return tbkProductDao.getTbkProductByPid(map);
	}

	@Override
	public List<TbkProduct> getTbkProductListPoint3(Map<String,Object> map) {
		return tbkProductDao.getTbkProductListPoint3(map);
	}

	@Override
	public TbkProduct getTbkProductById(Integer id) {
		return tbkProductDao.getTbkProductById(id);
	}

	@Override
	public void deleteTbkProductAll() {
		tbkProductDao.deleteTbkProductAll();
	}

	@Override
	public List<TbkProduct> getTbkProductListAll() {
		return tbkProductDao.getTbkProductListAll();
	}

	@Override
	public void saveTbkProductList(List<TbkProduct> tlist) {
		tbkProductDao.saveTbkProductList(tlist);
	}

	@Override
	public void saveTbkProductObj(TbkProduct tu) {
		tbkProductDao.saveTbkProductObj(tu);
	}

	@Override
	public void deleteTbkProductByDate(String date) {
		tbkProductDao.deleteTbkProductByDate(date);
	}

	@Override
	public void updateTbkProductDate(TbkProduct tp) {
		tbkProductDao.updateTbkProductDate(tp);
	}

	@Override
	public List<TbkProduct> getTbkProductListNoTkl() {
		return tbkProductDao.getTbkProductListNoTkl();
	}

	@Override
	public void updateTbkProductTkl(TbkProduct tp) {
		tbkProductDao.updateTbkProductTkl(tp);
	}

	@Override
	public void updateTbkBanner(Map<String, Object> map) {
		tbkProductDao.updateTbkBanner(map);
	}

	@Override
	public void updateTbkBannerStatus(Map<String, Object> map) {
		tbkProductDao.updateTbkBannerStatus(map);
	}

	@Override
	public List<TbkBanner> getBannerList() {
		return tbkProductDao.getBannerList();
	}

	@Override
	public List<TbkBanner> getBannerListAll() {
		return tbkProductDao.getBannerListAll();
	}

	@Override
	public void updateTbkProductPointRandom(Map<String, Object> map) {
		tbkProductDao.updateTbkProductPointRandom(map);
	}

	@Override
	public List<TypeNum> getTypeNum() {
		return tbkProductDao.getTypeNum();
	}

	@Override
	public TbkType getType(Map<String, Object> map) {
		return tbkProductDao.getType(map);
	}

	@Override
	public List<TbkType> getTypeList() {
		return tbkProductDao.getTypeList();
	}
	
	public String getSiteType(String taobaoType){
		String sitetype="";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("taobaotype",taobaoType);
		TbkType t=tbkProductDao.getType(map);
		if(t!=null){
			sitetype=t.getTypename();
		}
		return sitetype;
	}
	
	public Integer getSiteSort(String taobaoType){
		Integer sort=1;
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("typename",taobaoType);
		TbkType t=tbkProductDao.getType(map);
		if(t!=null){
			sort=t.getSort();
		}
		return sort;
	}
	
	public boolean isImport(String taobaoType,Double yhbl,Double srbl,Double yj,Integer salenum,Integer leftcnum,String ccontent){
		boolean bool=false;
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("typename",taobaoType);
		map.put("yhbl",yhbl);
		map.put("srbl",srbl);
		map.put("yj",yj);
		map.put("salenum",salenum);
		TbkType t=tbkProductDao.getType(map);
		if(t!=null&&leftcnum>0&&ccontent!=null&&!ccontent.equals("无")&&!ccontent.equals("")){
			bool=true;
		}
		return bool;
	}

	@Override
	public void updateTbkProductShow(String typename) {
		tbkProductDao.updateTbkProductShow(typename);
	}

	@Override
	public Group getGroupById(Integer id) {
		return tbkProductDao.getGroupById(id);
	}

	@Override
	public Group getGroupByName(String name) {
		return tbkProductDao.getGroupByName(name);
	}

	@Override
	public TbkProduct getTbkProductByNiId(String pid) {
		TbkProduct t=null;
		List<TbkProduct> plist=tbkProductDao.getTbkProductByNiId(pid);
		if(plist!=null&&plist.size()>0){
			t=plist.get(0);
		}
		return t;
	}

	@Override
	public void deleteTbkProductLast7Date(String date) {
		tbkProductDao.deleteTbkProductLast7Date(date);
	}

	@Override
	public TbkProduct searchTbkProduct(String searchkey,Integer start) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("searchkey", searchkey);
		map.put("start", start);
		return tbkProductDao.searchTbkProduct(map);
	}

	@Override
	public Group getGroupByNameAndUid(String name, Integer uid) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("groupname", name);
		map.put("uid", uid);
		return tbkProductDao.getGroupByNameAndUid(map);
	}

	@Override
	public TbkProduct getTbkProductPaoliang(Integer start) {
		return tbkProductDao.getTbkProductPaoliang(start);
	}

	@Override
	public TbkProduct getTbkProductSelect(Integer uid,Integer start) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("uid", uid);
		map.put("start", start);
		return tbkProductDao.getTbkProductSelect(map);
	}

	@Override
	public TbkProduct getTbkProductTop100(Integer start) {
		return tbkProductDao.getTbkProductTop100(start);
	}

	@Override
	public Integer getTbkProductSelectCount(Integer uid) {
		return tbkProductDao.getTbkProductSelectCount(uid);
	}

	@Override
	public void saveTbkProductPaoliangObj(TbkProduct tu) {
		tbkProductDao.saveTbkProductPaoliangObj(tu);
	}

	@Override
	public void saveTbkProductTop100Obj(TbkProduct tu) {
		tbkProductDao.saveTbkProductTop100Obj(tu);
	}

	@Override
	public void deleteTbkProductPaoliangAll() {
		tbkProductDao.deleteTbkProductPaoliangAll();
	}

	@Override
	public void deleteTbkProductTop100All() {
		tbkProductDao.deleteTbkProductTop100All();
	}

	@Override
	public TbkProduct getTbkProductPaoliangByPid(Map<String, Object> map) {
		return tbkProductDao.getTbkProductPaoliangByPid(map);
	}

	@Override
	public TbkProduct getTbkProductTop100ByPid(Map<String, Object> map) {
		return tbkProductDao.getTbkProductTop100ByPid(map);
	}

	@Override
	public TbkProduct getTbkProductTotal(String start) {
		return tbkProductDao.getTbkProductTotal(start);
	}

	@Override
	public List<TbkProduct> getTbkProductListNoTg() {
		return tbkProductDao.getTbkProductListNoTg();
	}

	@Override
	public void updateTbkProductTg(TbkProduct tp) {
		tbkProductDao.updateTbkProductTg(tp);
	}

	@Override
	public TbkPoint getTbkPointByDateAndWxname(TbkPoint tp) {
		return tbkProductDao.getTbkPointByDateAndWxname(tp);
	}

	@Override
	public List<TbkSr> getTbkSrByOrderno(String orderno,String pid,String fee) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("orderno",orderno);
		map.put("pid",pid);
		map.put("fee", fee);
		return tbkProductDao.getTbkSrByOrderno(map);
	}

	@Override
	public void saveTbkPoint(TbkPoint tp) {
		tbkProductDao.saveTbkPoint(tp);
	}

	@Override
	public void saveTbkSr(TbkSr sr) {
		tbkProductDao.saveTbkSr(sr);
	}

	@Override
	public void updateTbkPoint(TbkPoint tp) {
		tbkProductDao.updateTbkPoint(tp);
	}

	@Override
	public void updateTbkSr(TbkSr sr) {
		tbkProductDao.updateTbkSr(sr);
	}

	@Override
	public List<TbkSr> getTbkSrListByTime(Map<String, Object> map) {
		return tbkProductDao.getTbkSrListByTime(map);
	}

	@Override
	public TbkPoint getTbkPointByTimeAndWxname(Map<String, Object> map) {
		return tbkProductDao.getTbkPointByTimeAndWxname(map);
	}

	@Override
	public List<Group> getGroupByPid(Integer pid) {
		return tbkProductDao.getGroupByPid(pid);
	}

	@Override
	public void updateTbkProductSend(String id) {
		tbkProductDao.updateTbkProductSend(id);
	}

	@Override
	public void saveTbkDaySend(Map<String, Object> map) {
		tbkProductDao.saveTbkDaySend(map);
	}

	@Override
	public List<TbkDaySend> getTbkDaySend(Map<String, Object> map) {
		return tbkProductDao.getTbkDaySend(map);
	}

	@Override
	public Integer getTbkDaySendCount(Map<String, Object> map) {
		return tbkProductDao.getTbkDaySendCount(map);
	}

	@Override
	public void updateTbkProductJh(String id,String srbl) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("id",id);
		map.put("srbl",srbl);
		tbkProductDao.updateTbkProductJh(map); 
	}
	
	@Override
	public void updateTbkProductTy(String id,String srbl) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("id",id);
		map.put("srbl",srbl);
		tbkProductDao.updateTbkProductTy(map); 
	}
	
	@Override
	public void updateTbkProductQqhd(String id,String srbl) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("id",id);
		map.put("srbl",srbl);
		tbkProductDao.updateTbkProductQqhd(map); 
	}

	@Override
	public List<Group> getGroupByPidAndNoZd(Integer pid) {
		return tbkProductDao.getGroupByPidAndNoZd(pid);
	}

	@Override
	public List<Group> getGroupByPidAndZd(Integer pid) {
		return tbkProductDao.getGroupByPidAndZd(pid);
	}

	@Override
	public List<Group> getAllGroupList() {
		return tbkProductDao.getAllGroupList();
	}

	@Override
	public void deleteTbkProductByDtkid(String dtkid) {
		tbkProductDao.deleteTbkProductByDtkid(dtkid);
	}

	@Override
	public void saveTbkGroupSend(Integer tpid) {
		tbkProductDao.saveTbkGroupSend(tpid);
	}

	@Override
	public void updateTbkGroup(String fee,String wxname) {
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("fee",fee);
		map.put("wxname",wxname);
		tbkProductDao.updateTbkGroup(map);
	}

	@Override
	public void insertTbkAccountRecord(Map<String, Object> map) {
		tbkProductDao.insertTbkAccountRecord(map);
	}

	@Override
	public void updateTbkGroupYg(String fee, String wxname) {
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("ygfee",fee);
		map.put("wxname",wxname);
		tbkProductDao.updateTbkGroupYg(map);
	}

	@Override
	public Integer getTbkAccountRecord(Map<String, Object> map) {
		return tbkProductDao.getTbkAccountRecord(map);
	}

	@Override
	public Group getGroupByWxName(String name) {
		Group g=null;
		List<Group> glist=tbkProductDao.getGroupByWxName(name);
		if(glist.size()>0){
			g=glist.get(0); 
		}
		return g;
	}

	@Override
	public void deleteTbkProductOutTime() {
		tbkProductDao.deleteTbkProductOutTime();
	}

	@Override
	public List<TbkSr> getTbkSrByOrdernoNoFee(String orderno, String pid) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("orderno",orderno);
		map.put("pid",pid);
		return tbkProductDao.getTbkSrByOrdernoNoFee(map);
	}

	@Override
	public TbkProduct getTlmSearch(String pid, Integer gid, String createdate) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("gid",gid);
		map.put("pid",pid);
		map.put("createdate",createdate);
		return tbkProductDao.getTlmSearch(map);
	}

	@Override
	public void saveTlmSearch(TbkProduct tp) {
		tbkProductDao.saveTlmSearch(tp);
	}

	@Override
	public Integer getTbkDaySendByPid(Integer pid) {
		return tbkProductDao.getTbkDaySendByPid(pid);
	}

	@Override
	public Integer getTbkProductCountByType(String typename) {
		return tbkProductDao.getTbkProductCountByType(typename);
	}

}
