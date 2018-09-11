package com.limon.base.init.task;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.limon.base.model.TbkSr;
import com.limon.base.service.TbkConfigService;
import com.limon.base.service.TbkProductService;
import com.limon.base.taobao.Group;
import com.limon.util.CommonUtil;
import com.limon.util.DateUtil;

/**
 * 定时更新产品库
 * @author gqf
 *
 */
@Component
public class UpdateDataTask extends TimerTask{
	@Autowired
	private TbkProductService tbkProductService;
	@Autowired
	private TbkConfigService tbkConfigService;
	private DecimalFormat df = CommonUtil.getDoubleFormat();
	private Double aftertax=0.8;
	
	@Override
	public void run() {
		updateTg();
	}
	
	public void updateTg(){
		String cookie=tbkConfigService.getTbkConfig("tblmcookie").getCvalue();
		downsr(cookie);
	}
	
	public void downsr(String cookie){
		try{
			String apiurl="http://pub.alimama.com/report/getTbkPaymentDetails.json?spm=a219t.7664554.1998457203.59.dyLB74&queryType=1&payStatus=&DownloadID=DOWNLOAD_REPORT_INCOME_NEW&startTime="+DateUtil.getLastDay(-30)+"&endTime="+DateUtil.getToday();
			HttpClient client=new HttpClient();
			GetMethod get=new GetMethod(apiurl);
			//System.out.println(apiurl);
			get.setRequestHeader("Connection","keep-alive");
			get.setRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			get.setRequestHeader("Upgrade-Insecure-Requests","1");
			get.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
			get.setRequestHeader("Referer","http://pub.alimama.com/myunion.htm?spm=a219t.7664554.1998457203.59.dyLB74");
			get.setRequestHeader("Cookie", cookie);
			client.executeMethod(get);
			if(get.getStatusCode()==200){
				InputStream stream=get.getResponseBodyAsStream();
				HSSFWorkbook workbook = new HSSFWorkbook(stream);
				HSSFSheet sheet = workbook.getSheetAt(0);
				HSSFRow row ;
				int rows=sheet.getLastRowNum();
				System.out.println(DateUtil.getTodayTime()+":开始更新报表共"+rows+"条");
				int c=0;
				Map<String,Integer> map=new HashMap<String,Integer>();
				for(int icount=1;icount<rows;icount++){
		            row = sheet.getRow(icount);
		            String adid=row.getCell(29).getStringCellValue();
		            String adname=row.getCell(30).getStringCellValue();
		            String createtime=row.getCell(0).getStringCellValue();
		            String pointtime=row.getCell(1).getStringCellValue();
		            String fcbl=row.getCell(11).getStringCellValue();
		            String fee=row.getCell(12).getNumericCellValue()+"";
		            String orderno=row.getCell(25).getStringCellValue();
		            String pid=row.getCell(3).getStringCellValue();
		            String plat=row.getCell(9).getStringCellValue();
		            String pname=row.getCell(2).getStringCellValue();
		            String pnum=(int)row.getCell(6).getNumericCellValue()+"";
		            String price=row.getCell(7).getNumericCellValue()+"";
		            String sname=row.getCell(5).getStringCellValue();
		            String srbl=row.getCell(10).getStringCellValue();
		            String status=row.getCell(8).getStringCellValue();
		            String typename=row.getCell(26).getStringCellValue();
		            String yg=row.getCell(13).getNumericCellValue()+"";
		            String yj=row.getCell(18).getNumericCellValue()+"";
		            String yjbl=row.getCell(17).getStringCellValue();
		        
		            Integer s=map.get(row.getCell(25).getStringCellValue()+"-"+pid);
					if(s==null){
						map.put(row.getCell(25).getStringCellValue()+"-"+pid,1);
						s=1;
					}else{
						map.put(row.getCell(25).getStringCellValue()+"-"+pid,s+1);
						s=s+1;
					}
					orderno=s+orderno;
		            
		            List<TbkSr> tlist=tbkProductService.getTbkSrByOrdernoNoFee(orderno, pid);
			        if(tlist.size()>0){
			        	if(tlist.get(0).getStatus().equals("订单付款")&&(status.equals("订单结算")||status.equals("订单失效"))){
			        		c++;
				        	//更新已有的数据
							TbkSr tt=new TbkSr();
							tt.setId(tlist.get(0).getId());
							tt.setAdid(adid);
							tt.setAdname(adname);
							tt.setCreatetime(createtime);
							tt.setFcbl(fcbl);
							tt.setFee(fee);
							tt.setOrderno(orderno);
							tt.setPid(pid);
							tt.setPlat(plat);
							tt.setPname(pname);
							tt.setPnum(pnum);
							tt.setPointtime(pointtime);
							tt.setPrice(price);
							tt.setSname(sname);
							tt.setSrbl(srbl);
							tt.setStatus(status);
							tt.setTypename(typename);
							if(status.equals("订单失效")){
								yg="0.0";
								yj="0.0";
							}
							//扣税15%
							Double syg=Double.parseDouble(yg);
							String sfyg=df.format(aftertax*syg);
							Double syj=Double.parseDouble(yj);
							String sfyj=df.format(aftertax*syj);
							tt.setYg(sfyg);
							tt.setYj(sfyj);
	
							tt.setYjbl(yjbl);
							System.out.println(DateUtil.getTodayTime()+":"+icount+"更新="+orderno+"="+pid+"="+status);
							tbkProductService.updateTbkSr(tt);
			        	}
					}else{
						c++;
						TbkSr ts=new TbkSr();
						ts.setAdid(adid);
						if(adname==null||adname.equals("")){
							adname="天天淘惠";
						}
						ts.setAdname(adname);
						ts.setCreatetime(createtime);
						ts.setFcbl(fcbl);
						ts.setFee(fee);
						ts.setOrderno(orderno);
						ts.setPid(pid);
						ts.setPlat(plat);
						ts.setPname(pname);
						ts.setPnum(pnum);
						ts.setPointtime(pointtime);
						ts.setPrice(price);
						ts.setSname(sname);
						ts.setSrbl(srbl);
						ts.setStatus(status);
						ts.setTypename(typename);
						//扣税15%
						Double syg=Double.parseDouble(yg);
						String sfyg=df.format(aftertax*syg);
						Double syj=Double.parseDouble(yj);
						String sfyj=df.format(aftertax*syj);
						ts.setYg(sfyg);
						ts.setYj(sfyj);
						
						ts.setYjbl(yjbl);
						System.out.println(DateUtil.getTodayTime()+":"+icount+"添加="+orderno+"="+pid+"="+status);
						tbkProductService.saveTbkSr(ts);
					}
			    }
			    System.out.println(DateUtil.getTodayTime()+":结束更新报表共"+c+"条");
			}else{
				System.out.println(DateUtil.getTodayTime()+":下载报表失败");
			}
			
			//佣金统计
			/*
			List<Group> glist=tbkProductService.getAllGroupList();
			for(Group g:glist){
				List<TbkSr> srlist=getSrList(g.getId());
				for(TbkSr sr:srlist){
					if(sr.getStatus().equals("订单结算")){
						Map<String,Object> map=new HashMap<String,Object>();
						map.put("pid",sr.getPid());
						map.put("orderno",sr.getOrderno());
						map.put("fee", sr.getYj());
						map.put("remark",sr.getStatus());
						map.put("wxname",g.getWxname());
						map.put("srid",sr.getId());
						Integer s=tbkProductService.getTbkAccountRecord(map);
						if(s==0){
							tbkProductService.updateTbkGroup(sr.getYj(), g.getWxname());
							tbkProductService.insertTbkAccountRecord(map);
						}
					}
				}
			}
			*/
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public List<TbkSr> getSrList(Integer id){
		String stime="2017-05-01";
		String etime=DateUtil.getToday();
		Group group=tbkProductService.getGroupById(id);
		String orderno="";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("stime",stime+" 00:00:00");
		map.put("etime",etime+" 23:59:59");
		map.put("adname",group.getWxname());
		map.put("orderno",orderno);
		List<TbkSr> srlist=tbkProductService.getTbkSrListByTime(map);
		Double js=0.0;
		Double ygj=0.0;
		Integer fk=0;
		for(TbkSr sr:srlist){
			Double yg=Double.parseDouble(sr.getYg())*((100.0-Double.parseDouble(group.getFc()+""))/100);
			Double yj=Double.parseDouble(sr.getYj())*((100.0-Double.parseDouble(group.getFc()+""))/100);
			sr.setYg(df.format(yg)+"");
			sr.setYj(df.format(yj)+"");
			ygj+=yg;//预估金额
			js+=yj;//结算金额
			fk+=1;//付款数
		}
	
		//代理佣金计算
		Double djs=0.0;
		Double dygj=0.0;
		
		//代理收入及订单
		if(group.getId()==1){
			//自己的查询
			List<TbkSr> list=addSelfDlsr(group,stime,etime,orderno);
			for(TbkSr sr:list){
				dygj+=Double.parseDouble(sr.getYg());
				djs+=Double.parseDouble(sr.getYj());
				fk+=1;
				srlist.add(sr);
			}
		}else{
			//2级代理查询
			List<TbkSr> list=addDlsr(group,stime,etime,orderno);
			for(TbkSr sr:list){
				dygj+=Double.parseDouble(sr.getYg());
				djs+=Double.parseDouble(sr.getYj());
				fk+=1;
				srlist.add(sr);
			}
		}
		return srlist;
	}
	
	/**
	 * 2级代理订单查询及佣金计算
	 * @param group
	 * @param date
	 * @return
	 */
	public List<TbkSr> addDlsr(Group group,String sdate,String edate,String orderno){
		List<TbkSr> srlist=new ArrayList<TbkSr>();
		Map<String,Object> m=new HashMap<String,Object>();
		m.put("stime",sdate+" 00:00:00");
		m.put("etime",edate+" 23:59:59");
		m.put("orderno", orderno);
		List<Group> gadlist=tbkProductService.getGroupByPid(group.getId());
		for(Group gp:gadlist){
			//1级代理查询
			m.put("adname",gp.getWxname());
			List<TbkSr> gslist=tbkProductService.getTbkSrListByTime(m);
			for(TbkSr sr:gslist){
				Double yg=Double.parseDouble(sr.getYg());
				Double yj=Double.parseDouble(sr.getYj());
				//1级代理分成25%
				Double dfc=group.getDlfc()/100.0;
				sr.setYg(df.format(yg*dfc));
				sr.setYj(df.format(yj*dfc));
				srlist.add(sr);
			}
			/************************************/
			if(gp.getIszd()!=1){
				List<Group> sadlist=tbkProductService.getGroupByPid(gp.getId());
				for(Group g:sadlist){
					//2级代理查询
					m.put("adname",g.getWxname());
					List<TbkSr> sslist=tbkProductService.getTbkSrListByTime(m);
					for(TbkSr sr:sslist){
						Double yg=Double.parseDouble(sr.getYg());
						Double yj=Double.parseDouble(sr.getYj());
						//2级代理分成15%
						Double dfc=group.getNdlfc()/100.0;
						sr.setYg(df.format(yg*dfc));
						sr.setYj(df.format(yj*dfc));
						srlist.add(sr);
					}
				}
			}
		}
		return srlist;
	}
	
	/**
	 * 自己的订单查询及佣金计算
	 * @param group
	 * @param date
	 * @return
	 */
	public List<TbkSr> addSelfDlsr(Group group,String sdate,String edate,String orderno){
		List<TbkSr> srlist=new ArrayList<TbkSr>();
		Map<String,Object> m=new HashMap<String,Object>();
		m.put("stime",sdate+" 00:00:00");
		m.put("etime",edate+" 23:59:59");
		m.put("orderno", orderno);
		List<Group> g1list=tbkProductService.getGroupByPid(group.getId());
		for(Group g1:g1list){
			//1级代理查询
			m.put("adname",g1.getWxname());
			List<TbkSr> s1list=tbkProductService.getTbkSrListByTime(m);
			for(TbkSr sr:s1list){
				Double yg=Double.parseDouble(sr.getYg());
				Double yj=Double.parseDouble(sr.getYj());
				//1级代理分成
				Double dfc=g1.getFc()/100.0;
				sr.setYg(df.format(yg*dfc));
				sr.setYj(df.format(yj*dfc));
				srlist.add(sr);
			}
			
			/************************************/
			List<Group> g2list=tbkProductService.getGroupByPid(g1.getId());
			for(Group g2:g2list){
				//2级代理查询
				m.put("adname",g2.getWxname());
				List<TbkSr> s2list=tbkProductService.getTbkSrListByTime(m);
				for(TbkSr sr:s2list){
					Double yg=Double.parseDouble(sr.getYg());
					Double yj=Double.parseDouble(sr.getYj());
					//2级代理分成25%
					Double dfc=group.getDlfc()/100.0;
					sr.setYg(df.format(yg*dfc));
					sr.setYj(df.format(yj*dfc));
					srlist.add(sr);
				}
				
				/************************************/
				List<Group> g3list=tbkProductService.getGroupByPid(g2.getId());
				for(Group g3:g3list){
					//3级代理查询
					m.put("adname",g3.getWxname());
					List<TbkSr> s3list=tbkProductService.getTbkSrListByTime(m);
					for(TbkSr sr:s3list){
						Double yg=Double.parseDouble(sr.getYg());
						Double yj=Double.parseDouble(sr.getYj());

						//查询上级代理是否是直代
						Group gl3=tbkProductService.getGroupById(g3.getParentid());
						Double dfc=0.0;
						if(gl3.getIszd()==1){
							//直代按25%分成
							dfc=group.getDlfc()/100.0;
						}else{
							//3级代理分成20%
							dfc=group.getNdlfc()/100.0;
						}
						
						sr.setYg(df.format(yg*dfc));
						sr.setYj(df.format(yj*dfc));
						srlist.add(sr);
					}
					
					/************************************/
					List<Group> g4list=tbkProductService.getGroupByPid(g3.getId());
					for(Group g4:g4list){
						//4级代理查询
						m.put("adname",g4.getWxname());
						List<TbkSr> s4list=tbkProductService.getTbkSrListByTime(m);
						for(TbkSr sr:s4list){
							Double yg=Double.parseDouble(sr.getYg());
							Double yj=Double.parseDouble(sr.getYj());
							
							//查询上级代理是否是直代
							Group gl4=tbkProductService.getGroupById(g4.getParentid());
							Double dfc=0.0;
							if(gl4.getIszd()==1){
								//直代按25%分成
								dfc=group.getDlfc()/100.0;
							}else{
								//4级代理分成20%
								dfc=group.getNdlfc()/100.0;
							}

							sr.setYg(df.format(yg*dfc));
							sr.setYj(df.format(yj*dfc));
							srlist.add(sr);
						}
						
						/************************************/
						List<Group> g5list=tbkProductService.getGroupByPid(g4.getId());
						for(Group g5:g5list){
							//5级代理查询
							m.put("adname",g5.getWxname());
							List<TbkSr> s5list=tbkProductService.getTbkSrListByTime(m);
							for(TbkSr sr:s5list){
								Double yg=Double.parseDouble(sr.getYg());
								Double yj=Double.parseDouble(sr.getYj());

								//查询上级代理是否是直代
								Group gl5=tbkProductService.getGroupById(g5.getParentid());
								Double dfc=0.0;
								if(gl5.getIszd()==1){
									//直代按25%分成
									dfc=group.getDlfc()/100.0;
								}else{
									//5级代理分成20%
									dfc=group.getNdlfc()/100.0;
								}
								
								sr.setYg(df.format(yg*dfc));
								sr.setYj(df.format(yj*dfc));
								srlist.add(sr);
							}
							
							/************************************/
							List<Group> g6list=tbkProductService.getGroupByPid(g5.getId());
							for(Group g6:g6list){
								//6级代理查询
								m.put("adname",g6.getWxname());
								List<TbkSr> s6list=tbkProductService.getTbkSrListByTime(m);
								for(TbkSr sr:s6list){
									Double yg=Double.parseDouble(sr.getYg());
									Double yj=Double.parseDouble(sr.getYj());

									//查询上级代理是否是直代
									Group gl6=tbkProductService.getGroupById(g6.getParentid());
									Double dfc=0.0;
									if(gl6.getIszd()==1){
										//直代按25%分成
										dfc=group.getDlfc()/100.0;
									}else{
										//6级代理分成20%
										dfc=group.getNdlfc()/100.0;
									}
									
									sr.setYg(df.format(yg*dfc));
									sr.setYj(df.format(yj*dfc));
									srlist.add(sr);
								}
							}
						}
					}
				}
			}
		}
		return srlist;
	}
	
	public static void main(String[] args) {
		UpdateDataTask udt=new UpdateDataTask();
		udt.run();
	}

	public TbkProductService getTbkProductService() {
		return tbkProductService;
	}

	public void setTbkProductService(TbkProductService tbkProductService) {
		this.tbkProductService = tbkProductService;
	}
}
