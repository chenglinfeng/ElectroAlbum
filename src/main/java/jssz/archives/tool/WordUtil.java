package jssz.archives.tool;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

public class WordUtil {
	private CTSdtBlock contentBlock;
//	private CTSdtBlock sumerizeContentBlock;
	private CustomizeDocument customXWPFDocument;

	public WordUtil(CustomizeDocument customXWPFDocument) {
		super();
		//this.sumerizeContentBlock = customXWPFDocument.getDocument().getBody().addNewSdt();
		this.contentBlock = customXWPFDocument.getDocument().getBody().addNewSdt();
		this.customXWPFDocument = customXWPFDocument;
		//this.sumerizeContentBlock=customXWPFDocument.getDocument().getBody().addNewSdt();
	}

	public CTSdtBlock getBlock() {
		return contentBlock;
	}

	public void setBlock(CTSdtBlock block) {
		this.contentBlock = block;
	}

	public CustomizeDocument getCustomXWPFDocument() {
		return customXWPFDocument;
	}

	public void setCustomXWPFDocument(CustomizeDocument customXWPFDocument) {
		this.customXWPFDocument = customXWPFDocument;
	}

	public void generateContent(TreeMap<jssz.archives.tool.FileNo, jssz.archives.tool.PhotoInfo> photoInfoMap, TreeMap<String, String> groupNameMap) {
		//预处理，获取每一组照片的数量
		TreeMap<jssz.archives.tool.CompositeGroupNo,Integer> contentmap=new TreeMap<jssz.archives.tool.CompositeGroupNo, Integer>();
		for(jssz.archives.tool.PhotoInfo photoInfo:photoInfoMap.values()) {
			jssz.archives.tool.CompositeGroupNo compositeGroupNo = new jssz.archives.tool.CompositeGroupNo(photoInfo.getFileNo());
			if(!contentmap.containsKey(compositeGroupNo)) {
				contentmap.put(compositeGroupNo, new Integer(1));
			}else {
				int i=contentmap.get(compositeGroupNo);
				contentmap.put(compositeGroupNo, i+1);
			}
			
		}
		System.out.println("盒号和每组的个数"+contentmap);
		
		addTitle(contentBlock);
		addItem2TOC(contentmap,groupNameMap,photoInfoMap.entrySet().size());
		addNewPage(BreakType.PAGE);

	}
	
	
//	public void generateSimpleContent(TreeMap<String, String> eachClassificationName,
//			TreeMap<FileNo, PhotoInfo> photoInfoMap) {
//		 TreeMap<String, Integer> eachClassificationCountMap = new TreeMap<String, Integer>();
//		for (Entry<FileNo, PhotoInfo> entry : photoInfoMap.entrySet()) {
//			if (eachClassificationCountMap.get(entry.getValue().getClassificationNO()) == null) {
//				eachClassificationCountMap.put(entry.getValue().getClassificationNO(), new Integer(1));
//			} else {
//				int count = eachClassificationCountMap.get(entry.getValue().getClassificationNO());
//				eachClassificationCountMap.put(entry.getValue().getClassificationNO(), new Integer(count + 1));
//			}
//		}
//		addTitle(sumerizeContentBlock);
//		addItem2TOC(eachClassificationCountMap, eachClassificationName);
//		addNewPage(BreakType.PAGE);
//
//	}


	private void addTitle(CTSdtBlock block) {
		CTSdtPr sdtPr = block.addNewSdtPr();
		CTDecimalNumber id = sdtPr.addNewId();
		//上边距
		id.setVal(new BigInteger("4844945"));
		sdtPr.addNewDocPartObj().addNewDocPartGallery().setVal("Table of contents");
		CTSdtEndPr sdtEndPr = block.addNewSdtEndPr();
		CTRPr rPr = sdtEndPr.addNewRPr();
		CTFonts fonts = rPr.addNewRFonts();
		fonts.setAsciiTheme(STTheme.MINOR_H_ANSI);
		fonts.setEastAsiaTheme(STTheme.MINOR_H_ANSI);
		fonts.setHAnsiTheme(STTheme.MINOR_H_ANSI);
		fonts.setCstheme(STTheme.MINOR_BIDI);
		rPr.addNewB().setVal(STOnOff.OFF);
		rPr.addNewBCs().setVal(STOnOff.OFF);
		rPr.addNewColor().setVal("auto");
		rPr.addNewSz().setVal(new BigInteger("24"));
		rPr.addNewSzCs().setVal(new BigInteger("24"));
		CTSdtContentBlock content = block.addNewSdtContent();
		CTP p = content.addNewP();
		p.addNewPPr().addNewPStyle().setVal("TOCHeading");
		p.addNewR().addNewT().setStringValue("目     录");
		CTPPr pr = p.getPPr();
		CTJc jc = pr.isSetJc() ? pr.getJc() : pr.addNewJc();
		STJc.Enum en = STJc.Enum.forInt(ParagraphAlignment.CENTER.getValue());
		jc.setVal(en);
		CTRPr pRpr = p.getRArray(0).addNewRPr();
		fonts = pRpr.isSetRFonts() ? pRpr.getRFonts() : pRpr.addNewRFonts();
		fonts.setAscii("Times New Roman");
		fonts.setEastAsia("华文中宋");
		fonts.setHAnsi("华文中宋");
		// "目录"二字加粗
		CTOnOff bold = pRpr.isSetB() ? pRpr.getB() : pRpr.addNewB();
		bold.setVal(STOnOff.TRUE);
		// 设置“目录”二字字体大小
		CTHpsMeasure sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
		sz.setVal(new BigInteger("44"));
	}

	// 创建目录的页码，由于每页的照片数量为2个，所以用标题数除以2向上取整
//	private void addItem2TOC(TreeMap<FileNo, PhotoInfo> photoInfoMap,TreeMap<String, String> groupNameMap) {
//		
//		
//		
//		
//		int count = 0;
//		int num=1;		
//		List<ContentItem> list=new ArrayList<ContentItem>();
//		for (Map.Entry<FileNo, PhotoInfo> entry : photoInfoMap.entrySet()) {
//			count++;
//			//addEachItem(entry.getValue().getDesp(), (int) Math.ceil(count / 2.0),block);
//			//addEachItem(entry.getValue().getFileNo(), (int) Math.ceil(count / 2.0),contentBlock);
//			//	处理最后一位，如果为1，就记录前面几位和页数
//			int fileNoInGroup = entry.getKey().getFileNoInGroup();
//			if(fileNoInGroup==1) {
//				String key=entry.getKey().getFileNo().substring(0,entry.getKey().getFileNo().lastIndexOf("-"));
//				String title=num+"."+groupNameMap.get(key);
//				String pageNum=String.valueOf((int) Math.ceil(count / 2.0));
//				list.add( new ContentItem(key, title, pageNum));
//				num++;
//			}
//		}
//		for(int i=0;i<list.size();i++) {
//			if(i==list.size()-1) {
//				addEachItem(list.get(i).getName(), list.get(i).getPageNum()+"-"+(int) Math.ceil(photoInfoMap.keySet().size() / 2.0),contentBlock);
//			}else {
//				addEachItem(list.get(i).getName(), list.get(i).getPageNum(),contentBlock);
//			}
//			System.out.println("生成目录条目"+(i+1)+":组号："+list.get(i).getId()+",名字："+list.get(i).getName()+",页码："+list.get(i).getPageNum());
//		}
//
//	}
//	
	
private void addItem2TOC(TreeMap<jssz.archives.tool.CompositeGroupNo,Integer> contentmap, TreeMap<String, String> groupNameMap, int total) {
		
		
		
		
		int count = 1;
		int num=1;		
		List<jssz.archives.tool.ContentItem> list=new ArrayList<jssz.archives.tool.ContentItem>();
		for (Entry<jssz.archives.tool.CompositeGroupNo,Integer> entry : contentmap.entrySet()) {
			
			//addEachItem(entry.getValue().getDesp(), (int) Math.ceil(count / 2.0),block);
			//addEachItem(entry.getValue().getFileNo(), (int) Math.ceil(count / 2.0),contentBlock);
			//	处理最后一位，如果为1，就记录前面几位和页数
			
				String key=entry.getKey().getCompositeFileNo();
				String title=num+"."+groupNameMap.get(key);
				String pageNum=String.valueOf((int) Math.ceil( count/ 2.0));
				count+=entry.getValue();
				list.add( new jssz.archives.tool.ContentItem(key, title, pageNum));
				num++;
		}
		int numTOC = list.size()/30+1;
		for(int k=0;k<numTOC;k++){
			int num2 =30;
			if(k<numTOC-1){
				num2 = 30;
			}else{
				num2 = list.size()%30;
			}
			for(int i=0;i<num2;i++) {
				if((i+k*30)==list.size()-1) {
					addEachItem(list.get(i+k*30).getName(), list.get(i+k*30).getPageNum()+"-"+(int) Math.ceil(total / 2.0),contentBlock);
				}else {
					addEachItem(list.get(i+k*30).getName(), list.get(i+k*30).getPageNum(),contentBlock);
				}
				System.out.println("生成目录条目"+(i+k*30+1)+":组号："+list.get(i+k*30).getId()+",名字："+list.get(i+k*30).getName()+",页码："+list.get(i+k*30).getPageNum());
			}
//
//			addSimpleParagraph("第" + k + "页", "宋体", 9, "808080", false,
//					false, "Heading2", ParagraphAlignment.CENTER, 0);
//			addNewPage(BreakType.PAGE);
		}

		
//		for(int i=0;i<list.size();i++) {
//			if(i==list.size()-1) {
//				addEachItem(list.get(i).getName(), list.get(i).getPageNum()+"-"+(int) Math.ceil(total / 2.0),contentBlock);
//			}else {
//				addEachItem(list.get(i).getName(), list.get(i).getPageNum(),contentBlock);
//			}
//			System.out.println("生成目录条目"+(i+1)+":组号："+list.get(i).getId()+",名字："+list.get(i).getName()+",页码："+list.get(i).getPageNum());
//		}


	}
	
	
//	private void addItem2TOC(TreeMap<String, Integer> eachClassificationCount,TreeMap<String, String>eachClassificationName) {
//		int count = 1;
//		for (Map.Entry<String, String> entry : eachClassificationName.entrySet()) {
//			if(eachClassificationCount.get(entry.getKey())!=null){
//			addEachItem(entry.getKey()+"----"+entry.getValue(), (int) Math.ceil(count / 2.0),sumerizeContentBlock);
//			count+=eachClassificationCount.get(entry.getKey());
//			}
//
//		}
//
//	}
//	

	private void addEachItem(String title, String page,CTSdtBlock block) {
		//CTSdtContentBlock contentBlock = this.block.getSdtContent();
		CTSdtContentBlock contentBlock = block.getSdtContent();
		CTP p = contentBlock.addNewP();
		CTPPr pPr = p.addNewPPr();
		CTTabs tabs = pPr.addNewTabs();
		CTTabStop tab = tabs.addNewTab();
		tab.setVal(STTabJc.CENTER);
		tab.setLeader(STTabTlc.DOT);
		tab.setPos(new BigInteger("8900"));
		pPr.addNewRPr().addNewNoProof();
		CTR run = p.addNewR();
		run.addNewRPr().addNewNoProof();
		run.addNewT().setStringValue(title);
		CTRPr pRpr = run.getRPr();
		CTFonts fonts = pRpr.isSetRFonts() ? pRpr.getRFonts() : pRpr.addNewRFonts();
		fonts.setAscii("Times New Roman");
		fonts.setEastAsia("楷体");
		fonts.setHAnsi("楷体");
		CTHpsMeasure sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
		sz.setVal(new BigInteger("21"));
		CTHpsMeasure szCs = pRpr.isSetSzCs() ? pRpr.getSzCs() : pRpr.addNewSzCs();
		szCs.setVal(new BigInteger("21"));
		run = p.addNewR();
		run.addNewRPr().addNewNoProof();
		run.addNewTab();
		run = p.addNewR();
		run.addNewRPr().addNewNoProof();
		run.addNewFldChar().setFldCharType(STFldCharType.BEGIN);
		run = p.addNewR();
		run.addNewRPr().addNewNoProof();
		CTText text = run.addNewInstrText();
		text.setSpace(SpaceAttribute.Space.DEFAULT);
		text.setStringValue("HYPERLINK  \\l " + "_top");
		p.addNewR().addNewRPr().addNewNoProof();
		run = p.addNewR();
		run.addNewRPr().addNewNoProof();
		run.addNewFldChar().setFldCharType(STFldCharType.SEPARATE);
		run = p.addNewR();
		run.addNewRPr().addNewNoProof();
		run.addNewT().setStringValue(page);
		run = p.addNewR();
		run.addNewRPr().addNewNoProof();
		run.addNewFldChar().setFldCharType(STFldCharType.END);
		CTSpacing pSpacing = pPr.getSpacing() != null ? pPr.getSpacing() : pPr.addNewSpacing();
		pSpacing.setLineRule(STLineSpacingRule.AUTO);
		pSpacing.setLine(new BigInteger("360"));
	}

	public void createHeader(String orgFullName) throws Exception {
		/*
		 * 对页眉段落作处理
		 * */
		CTSectPr sectPr = customXWPFDocument.getDocument().getBody().addNewSectPr();
		XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(customXWPFDocument,sectPr);
		XWPFHeader header = headerFooterPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);
		XWPFParagraph paragraph = header.getParagraphArray(0);
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		paragraph.setBorderBottom(Borders.THICK);

		/*
		 * 添加字体页眉，公司全称
		 * */
		if (StringUtils.isNotEmpty(orgFullName)) {
			XWPFRun run = paragraph.createRun();
			run.setText(orgFullName);
			//setXWPFRunStyle(run,"新宋体",10);
		}
	}

	public void addSimpleParagraph(String text, String fontName, int fontSize, String color, boolean isBold,
			boolean isItalic, String heading, ParagraphAlignment paragraphAlignment, int blankRow) {
		XWPFParagraph xp = customXWPFDocument.createParagraph();
		xp.setStyle(heading);
		XWPFRun r1 = xp.createRun();
		r1.setText(text);
		r1.setFontSize(fontSize);
		r1.setBold(isBold);
		r1.setItalic(isItalic);
		r1.setColor(color);
		CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
		CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii(fontName);
		fonts.setEastAsia(fontName);
		fonts.setHAnsi(fontName);
		xp.setAlignment(paragraphAlignment);
		xp.setVerticalAlignment(TextAlignment.CENTER);
	}

	public void addNewPage(BreakType breakType) {
		XWPFParagraph xp = customXWPFDocument.createParagraph();
		xp.createRun().addBreak(breakType);
	}

	public void insertPicture(int compressPicSizeInKB,int pictureType,byte[] bytes,String pictureAbsolutePath, ParagraphAlignment paragraphAlignment, int width,
			int height) throws Exception {
		//FileInputStream photoStream = null;
	
			//System.out.println(pictureAbsolutePath);

//			photoStream = new FileInputStream(pictureAbsolutePath);
//			byte[] photoByte = new byte[photoStream.available()];
//			byte[] bytes=null;
//			if(pictureType==XWPFDocument.PICTURE_TYPE_JPEG) {
//			  bytes = FileUtils.readFileToByteArray(new File(pictureAbsolutePath));
//		     bytes = PhotoCompressor.compressPic(bytes, 100, pictureAbsolutePath);// 图片小于300kb
//			}else if(pictureType==XWPFDocument.PICTURE_TYPE_TIFF) {
//				bytes=PictureUtil.tifToJpg(pictureAbsolutePath);
//			}
			
			
//			photoStream.read(photoByte);
//			ByteArrayInputStream byteInputStream = new ByteArrayInputStream(photoByte);
		    byte[] compressBytes = jssz.archives.tool.PhotoCompressor.compressPic(bytes, compressPicSizeInKB, pictureAbsolutePath);// 图片小于300kb

			XWPFParagraph picture = customXWPFDocument.createParagraph();
			picture.setAlignment(paragraphAlignment);
			//customXWPFDocument.addPictureData(byteInputStream, pictureType);
			customXWPFDocument.addPictureData(compressBytes, pictureType);

			customXWPFDocument.createPicture(customXWPFDocument.getAllPictures().size() - 1, width, height, picture);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		} finally {
//			if (photoStream != null) {
//				try {
//					photoStream.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				} catch (Exception e) {
//					System.out.println("获取图片格式出错；" + e.getMessage());
//				}
//
//			}
//		}
	}

	public void addBlankRow(int count) {
		XWPFParagraph xp = customXWPFDocument.createParagraph();
		XWPFRun r1 = xp.createRun();
		for (int i = 0; i < count - 1; i++) {
			r1.addCarriageReturn();
		}

	}



	public void saveDocument(String savePath) throws Exception {
		FileOutputStream fos = new FileOutputStream(savePath);
		customXWPFDocument.write(fos);
		fos.close();
	}

	public void createPhotoInfoTable(jssz.archives.tool.PhotoInfo photoInfo) {
		XWPFTable table = customXWPFDocument.createTable(3, 3);
		CTTbl ttbl = table.getCTTbl();
		CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
		CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
		CTJc cTJc = tblPr.addNewJc();
		cTJc.setVal(STJc.CENTER);
		tblWidth.setW(new BigInteger("8000"));
		for (int i = 0; i < 3; i++) {
			table.getRow(i).setHeight(400);
		}
		tblWidth.setType(STTblWidth.DXA);
		CTTblLayoutType t = tblPr.isSetTblLayout() ? tblPr.getTblLayout() : tblPr.addNewTblLayout();
		t.setType(STTblLayoutType.FIXED);// 使布局固定，不随内容改变宽度

		mergeCell(table.getRow(1), 0, 3);
		mergeCell(table.getRow(2), 0, 3);

		// 设置第一行各列宽度
		XWPFTableRow firstRow = table.getRow(0);
		setColumnWidth(firstRow.getCell(0), 3500);
		setColumnWidth(firstRow.getCell(1), 1700);
		setColumnWidth(firstRow.getCell(2), 2200);
		addNewParagraph(firstRow.getCell(0), "档号：" + photoInfo.getFileNo(),
				getFontSize("档号：" + photoInfo.getFileNo(), jssz.archives.tool.ExcelColumName.FILE_NO), ParagraphAlignment.CENTER, TextAlignment.CENTER,"000000","宋体");
		addNewParagraph(firstRow.getCell(1), controlStringLength("时间：" + photoInfo.getPhotoTakenTime(), jssz.archives.tool.ExcelColumName.PHOTO_TAKEN_TIME),
				getFontSize("时间：" + photoInfo.getPhotoTakenTime(), jssz.archives.tool.ExcelColumName.PHOTO_TAKEN_TIME), ParagraphAlignment.CENTER,
				TextAlignment.CENTER,"000000","宋体");
		addNewParagraph(firstRow.getCell(2), controlStringLength("摄影者：" + photoInfo.getAuthor(), jssz.archives.tool.ExcelColumName.AUTHOR),
				getFontSize("摄影者：" + photoInfo.getAuthor(), jssz.archives.tool.ExcelColumName.AUTHOR), ParagraphAlignment.CENTER, TextAlignment.CENTER,"000000","宋体");
		addNewParagraph(table.getRow(1).getCell(0),"题名：" + photoInfo.getName(),
				getFontSize("题名：" + photoInfo.getName(), jssz.archives.tool.ExcelColumName.NAME), ParagraphAlignment.LEFT, TextAlignment.BOTTOM,"000000","宋体");
		addNewParagraph(table.getRow(2).getCell(0), controlStringLength("文字说明："  + photoInfo.getDescription(), jssz.archives.tool.ExcelColumName.DESCRIPTION),
				getFontSize("文字说明：" + photoInfo.getDescription(), jssz.archives.tool.ExcelColumName.DESCRIPTION), ParagraphAlignment.LEFT, TextAlignment.TOP,"000000","宋体");
		System.out.println("插入文字说明，档号="+photoInfo.getFileNo());

	}


	public void addNewParagraph(XWPFTableCell cell, String text, int fontSize, ParagraphAlignment paragraphAlignment,
			TextAlignment textAlignment,String color,String typeface) {
		//  设置文字方向
		cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
		CTTc cttc = cell.getCTTc();
		cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.LEFT);
		CTP ctP = (cttc.sizeOfPArray() == 0) ? cttc.addNewP() : cttc.getPArray(0);
		XWPFParagraph paragraph = new XWPFParagraph(ctP, cell);
		paragraph.setAlignment(paragraphAlignment);
		paragraph.setVerticalAlignment(textAlignment);
		paragraph.setSpacingLineRule(LineSpacingRule.EXACT);
		XWPFRun r = paragraph.createRun();
		r.setText(text);
		r.setFontSize(fontSize);
		r.setColor(color);
		CTRPr rpr = r.getCTR().isSetRPr() ? r.getCTR().getRPr() : r.getCTR().addNewRPr();
		CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii(typeface);
		fonts.setEastAsia(typeface);
		fonts.setHAnsi(typeface);
	}

	private void mergeCell(XWPFTableRow row, int start, int end) {
		for (int i = start; i < end; i++) {
			if (i == start) {
				row.getCell(i).getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
			} else {
				row.getCell(i).getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
			}
		}
	}

	private void setColumnWidth(XWPFTableCell cell, int cloumnWidth) {
		CTTc cttc = cell.getCTTc();
		CTTcPr cellPr = cttc.addNewTcPr();
		CTTblWidth ctTblWidth = cellPr.addNewTcW();
		ctTblWidth.setW(BigInteger.valueOf(cloumnWidth));
		ctTblWidth.setType(STTblWidth.DXA);
	}



	private int getFontSize(String text, jssz.archives.tool.ExcelColumName excelColumName) {
		int size = text.length();
		int res = 5;
		switch (excelColumName) {
		case FILE_NO:
			if (size <= 50) {
				res = 10;
			} else if (size <= 110) {
				res = 8;
			} else {
				res = 5;
			}
			break;
		case AUTHOR:
			if (size <= 20) {
				res = 10;
			} else if (size <= 30) {
				res = 8;
			} else {
				res = 5;
			}
			break;
		case PHOTO_TAKEN_TIME:
			if (size <= 25) {
				res = 10;
			} else if (size <= 35) {
				res = 8;
			} else {
				res = 5;
			}
			break;
		case NAME:
		case DESCRIPTION:
			if (size <= 70) {
				res = 10;
			} else if (size <= 80) {
				res = 9;
			} else if (size <= 140) {
				res = 8;
			} else if (size <= 160) {
				res = 7;
			} else if (size <= 190) {
				res = 6;
			} else {
				res = 5;
			}
			break;
		default:
			break;
		}
		return res;
	}


	
	private String controlStringLength(String s, jssz.archives.tool.ExcelColumName excelColumName) {
		int maxLength = excelColumName.getMaxLength();
		if (maxLength == -1 || s.length() <= maxLength) {
			return s;
		} else {

			return s.substring(0, maxLength) + "*";
		}
	}
	
	
	public static void  main(String args[]) throws IOException {
		FileInputStream photoStream = null;
	
//			int pictureType = PictureUtil.getPicType("/Users/sherry/Desktop/测试数据1/100802-ZP2018-0-003-002.tiff");
//			if (pictureType == -1) {
//				return;
//			}

//			photoStream = new FileInputStream(pictureAbsolutePath);
//			byte[] photoByte = new byte[photoStream.available()];

			
//			 byte[] bytes = FileUtils.readFileToByteArray(new File("/Users/sherry/Desktop/测试数据1/100802-ZP2018-0-003-002.tiff"));
//		     bytes = PhotoCompressor.compressPic(bytes, 100, "/Users/sherry/Desktop/测试数据1/100802-ZP2018-0-003-002.tiff");// 图片小于300kb
//			FileOutputStream fos = new FileOutputStream(new File("/Users/sherry/Desktop/测试数据1/100802-ZP2018-0-003-0022.tiff"));
//		      fos.write(bytes,0,bytes.length);   
//		      fos.flush();   
//		      fos.close();  
		throw new RuntimeException();
			
	}
	
	public void setMargin() {
		CTSectPr sectPr = customXWPFDocument.getDocument().getBody().addNewSectPr();
		CTPageMar pageMar = sectPr.addNewPgMar();
		pageMar.setTop(BigInteger.valueOf(220L));
		pageMar.setBottom(BigInteger.valueOf(200L));
		//pageMar.setRight(BigInteger.valueOf(1575L));
	}

}
