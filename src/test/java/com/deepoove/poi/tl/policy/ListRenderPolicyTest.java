package com.deepoove.poi.tl.policy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.NumberingFormat;
import com.deepoove.poi.data.NumberingRenderData;
import com.deepoove.poi.data.Numberings;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.Tables;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.Texts;
import com.deepoove.poi.policy.ListRenderPolicy;

@DisplayName("List Render test case")
public class ListRenderPolicyTest {

    private TableRenderData table;

    @BeforeEach
    public void init() {
        RowRenderData header, row0, row1, row2;
        header = Rows.of("姓名", "学历").textColor("FF00FF").create();
        row0 = Rows.of(Texts.of("张三").link("http://deepoove.com").create(), Texts.of("研究生").color("1E915D").create())
                .create();
        row1 = Rows.of("李四", "博士").create();
        row2 = Rows.of("王五", "博士后").create();
        table = Tables.of(header, row0, row1, row2).create();
    }

    @SuppressWarnings("serial")
    @Test
    public void testListData() throws Exception {

        final List<Object> list = new ArrayList<Object>() {
            {
                add(new TextRenderData("ver 0.0.3"));
                add(new PictureRenderData(100, 120, "src/test/resources/logo.png"));
                add(new TextRenderData("9d55b8", "Deeply in love with the things you love, just deepoove."));
                add(new TextRenderData("ver 0.0.4"));
                add(new PictureRenderData(100, 120, "src/test/resources/logo.png"));
                add(getData(NumberingFormat.DECIMAL_PARENTHESES));
                add(table);
            }
        };

        Map<String, Object> datas = new HashMap<String, Object>();
        datas.put("website", list);

        /*
         * List demo {{website}} List demo.
         */
        Configure config = Configure.builder().bind("website", new ListRenderPolicy() {
        }).build();
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/render_list.docx", config);
        template.render(datas).writeToFile("out_render_list.docx");
    }

    private NumberingRenderData getData(NumberingFormat format) {
        return Numberings.of(format)
                .addItem(new TextRenderData("df2d4f", "Deeply in love with the things you love, just deepoove."))
                .addItem(new TextRenderData("Deeply in love with the things you love, just deepoove."))
                .addItem(new TextRenderData("5285c5", "Deeply in love with the things you love, just deepoove."))
                .create();
    }

}
