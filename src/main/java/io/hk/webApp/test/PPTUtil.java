package io.hk.webApp.test;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.net.URL;
import java.util.Arrays;

import io.framecore.Tool.PropertiesHelp;
import io.hk.webApp.dto.ProductDTO;
import io.hk.webApp.vo.PptVO;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xslf.usermodel.*;
import org.bson.types.ObjectId;

import static org.apache.poi.sl.usermodel.TextParagraph.TextAlign.*;

public class PPTUtil {

    private static final String PATH = "path";

    public static String QINIU_PATH = "http://imghk.redaiyufx.cn/";

    public static void main(String[] args) throws Exception {

        PptVO vo = new PptVO();
        vo.setEmail("450883304@qq.com");
        vo.setMainTitle("主标题");
        vo.setSubhead("副标题");
        vo.setPhone("17750710052");
        vo.setPosition("职位");
        vo.setQq("450883304");
        vo.setUserName("联系人");
        vo.setProductIds(Arrays.asList("5ff6c001b554e43fa1159833", "5ff6c1621158c13c04f24773", "5ff6c1941158c13c04f24774", "5ff7c4282aa21a62ecf7d5cd"));
        createPPT(vo);
    }


    public static String createPPT(PptVO vo) throws Exception {
        //创建ppt对象
        XMLSlideShow ppt = new XMLSlideShow();
        //首页
        XSLFSlide slide0 = ppt.createSlide();
        XSLFSlide slide1 = ppt.createSlide();
        fillIndexContent(slide0, slide1, vo);

        {
            for (int q = 0; q < vo.getProductDTOS().size(); q++) {
                ProductDTO productDTO = vo.getProductDTOS().get(q);
                XSLFSlide slide = ppt.createSlide();
                /** 生成二进制数组 **/
//                String imagePath = "https://bkimg.cdn.bcebos.com/pic/0df3d7ca7bcb0a46f21f75783429e1246b600c330115?x-bce-process=image/watermark,image_d2F0ZXIvYmFpa2UxODA=,g_7,xp_5,yp_5";

                String imagePath = null;
                if (StringUtils.isNotEmpty(productDTO.getProduct().getImage1())) {
                    imagePath = productDTO.getProduct().getImage1();
                } else {
                    imagePath = "1";
                }
                String[] imgs = imagePath.split("==");

                for (int i = 0; i < imgs.length; i++) {
                    URL urlfile = new URL(QINIU_PATH + imgs[i]);
                    InputStream is = urlfile.openStream();
                    byte[] bytes = IOUtils.toByteArray(is);
                    XSLFPictureData idx = ppt.addPicture(bytes, XSLFPictureData.PictureType.JPEG);
                    if (i == 0) {
                        XSLFPictureShape pic = slide.createPicture(idx);
                        pic.setAnchor(new Rectangle2D.Double(0, 0, 300, 300));
                    }
                    if (i == 1) {
                        XSLFPictureShape pic1 = slide.createPicture(idx);
                        pic1.setAnchor(new Rectangle2D.Double(0, 300, 150, 200));
                    }
                    if (i == 2) {
                        XSLFPictureShape pic2 = slide.createPicture(idx);
                        pic2.setAnchor(new Rectangle2D.Double(150, 300, 150, 200));
                        break;
                    }
                }

                //文本框
                XSLFTextBox xslfTextBox = slide.createTextBox();
                xslfTextBox.setAnchor(new Rectangle(350, 50, 300, 400));
                xslfTextBox.setFlipHorizontal(true);

                //商品名
                XSLFTextParagraph paragraph = xslfTextBox.addNewTextParagraph();
                paragraph.setTextAlign(LEFT);

                XSLFTextRun xslfTextRun0 = paragraph.addNewTextRun();
                xslfTextRun0.setBold(true);
                xslfTextRun0.setFontSize(30.0);
                xslfTextRun0.setFontFamily("\u5b8b\u4f53 (\u6807\u9898)");
                xslfTextRun0.setText(productDTO.getProduct().getName() + "\n\n\n");
                //--------------------------------------------------------------

                //品牌名
                XSLFTextParagraph paragraph1 = xslfTextBox.addNewTextParagraph();
                paragraph.setTextAlign(LEFT);

                XSLFTextRun xslfTextRun01 = paragraph1.addNewTextRun();
                xslfTextRun01.setFontSize(20.0);
                xslfTextRun01.setFontFamily("\u5b8b\u4f53 (\u6807\u9898)");
                xslfTextRun01.setText("品牌：" + productDTO.getProduct().getBrandName() + "\n");
                //--------------------------------------------------------------

                //市场价含税
                XSLFTextParagraph paragraph2 = xslfTextBox.addNewTextParagraph();
                paragraph.setTextAlign(LEFT);

                XSLFTextRun xslfTextRun02 = paragraph2.addNewTextRun();
                xslfTextRun02.setFontSize(20.0);
                xslfTextRun02.setFontFamily("\u5b8b\u4f53 (\u6807\u9898)");
                xslfTextRun02.setText("市场价含税:" + productDTO.getFactoryNewMoneyTax() + "\n");
                //--------------------------------------------------------------

                //供货价含税
                XSLFTextParagraph paragraph3 = xslfTextBox.addNewTextParagraph();
                paragraph.setTextAlign(LEFT);

                XSLFTextRun xslfTextRun03 = paragraph3.addNewTextRun();
                xslfTextRun03.setFontSize(20.0);
                xslfTextRun03.setFontFamily("\u5b8b\u4f53 (\u6807\u9898)");
                xslfTextRun03.setText("供货价含税" + productDTO.getSalerNewMoneyTax() + "\n");
                //--------------------------------------------------------------
            }
        }
        String fileName = ObjectId.get() + ".pptx";
        ppt.write(new FileOutputStream(PropertiesHelp.getApplicationConf(PATH) + fileName));
        return fileName;
    }


    /**
     * 填充首页内容
     *
     * @param xslfSlide
     */
    private static void fillIndexContent(XSLFSlide xslfSlide, XSLFSlide xslfSlide1, PptVO vo) throws IOException {
        //文本框
        XSLFTextBox xslfTextBox = xslfSlide.createTextBox();
        xslfTextBox.setAnchor(new Rectangle(65, 25, 616, 480));
        xslfTextBox.setFlipHorizontal(true);

        //段落
        XSLFTextParagraph paragraph = xslfTextBox.addNewTextParagraph();
        paragraph.setTextAlign(CENTER);


        XSLFTextRun xslfTextRun0 = paragraph.addNewTextRun();
        xslfTextRun0.setBold(true);
        xslfTextRun0.setFontSize(44.0);
        //宋体 (标题)
        xslfTextRun0.setFontFamily("\u5b8b\u4f53 (\u6807\u9898)");
        xslfTextRun0.setText("\n\n\n");

        //主标题
        XSLFTextRun xslfTextRun = paragraph.addNewTextRun();
        xslfTextRun.setBold(true);
        xslfTextRun.setFontSize(44.0);
        //宋体 (标题)
        xslfTextRun.setFontFamily("\u5b8b\u4f53 (\u6807\u9898)");
        xslfTextRun.setText(vo.getMainTitle() + "\n\n");

        //副标题
        XSLFTextRun xslfTextRun1 = paragraph.addNewTextRun();
        xslfTextRun1.setBold(false);
        xslfTextRun1.setFontSize(24.0);
        //宋体 (正文)
        xslfTextRun1.setFontFamily("\u5b8b\u4f53 (\u6b63\u6587)");
        xslfTextRun1.setText(vo.getSubhead());

        //-------------------------------------------------------------------------------------------------------------------

        //文本框
        XSLFTextBox xslfTextBox1 = xslfSlide1.createTextBox();
        xslfTextBox1.setAnchor(new Rectangle(65, 25, 616, 480));
        xslfTextBox1.setFlipHorizontal(true);

        //段落
        XSLFTextParagraph paragraph1 = xslfTextBox1.addNewTextParagraph();
        paragraph1.setTextAlign(CENTER);

        XSLFTextRun xslfTextRun01 = paragraph1.addNewTextRun();
        xslfTextRun01.setBold(true);
        xslfTextRun01.setFontSize(30.0);
        //宋体 (标题)
        xslfTextRun01.setFontFamily("\u5b8b\u4f53 (\u6807\u9898)");
        xslfTextRun01.setText("\n\n");

        //联系人
        XSLFTextRun xslfTextRun11 = paragraph1.addNewTextRun();
        xslfTextRun11.setBold(false);
        xslfTextRun11.setFontSize(20.0);
        //宋体 (正文)
        xslfTextRun11.setFontFamily("\u5b8b\u4f53 (\u6b63\u6587)");
        xslfTextRun11.setText(vo.getUserName() + "\n\n\n");

        //职位
        XSLFTextRun xslfTextRun12 = paragraph1.addNewTextRun();
        xslfTextRun12.setBold(false);
        xslfTextRun12.setFontSize(20.0);
        //宋体 (正文)
        xslfTextRun12.setFontFamily("\u5b8b\u4f53 (\u6b63\u6587)");
        xslfTextRun12.setText(vo.getPosition() + "\n\n\n");

        //电话
        XSLFTextRun xslfTextRun13 = paragraph1.addNewTextRun();
        xslfTextRun13.setBold(false);
        xslfTextRun13.setFontSize(20.0);
        //宋体 (正文)
        xslfTextRun13.setFontFamily("\u5b8b\u4f53 (\u6b63\u6587)");
        xslfTextRun13.setText(vo.getPhone() + "\n\n\n");

        //邮箱
        XSLFTextRun xslfTextRun14 = paragraph1.addNewTextRun();
        xslfTextRun14.setBold(false);
        xslfTextRun14.setFontSize(20.0);
        //宋体 (正文)
        xslfTextRun14.setFontFamily("\u5b8b\u4f53 (\u6b63\u6587)");
        xslfTextRun14.setText(vo.getEmail() + "\n\n\n");

        //qq
        XSLFTextRun xslfTextRun15 = paragraph1.addNewTextRun();
        xslfTextRun15.setBold(false);
        xslfTextRun15.setFontSize(20.0);
        //宋体 (正文)
        xslfTextRun15.setFontFamily("\u5b8b\u4f53 (\u6b63\u6587)");
        xslfTextRun15.setText(vo.getQq() + "\n\n\n");
    }


}