package ru.lod.spbalert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.lod.spbalert.model.GroupInfo;
import ru.lod.spbalert.model.RequestInfo;
import ru.lod.spbalert.model.SpbAlert;
import ru.lod.spbalert.service.InfoAlertService;

@SpringBootTest
class SpbAlertApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(SpbAlertApplicationTests.class);

    @Autowired
    private InfoAlertService infoAlertService;

    @Test
    @Ignore
    public void testActuality() throws ParseException {
        GroupInfo groupInfo = new GroupInfo();
        List<SpbAlert> list = new ArrayList<String>() {
            {
                add("13:15:20");
                add("13:15:21");
                add("13:15:22");
                add("13:15:30");
                add("13:15:31");
                add("13:15:32");
                add("13:16:10");
                add("13:16:11");
                add("13:16:12");
                add("13:17:21");
                add("13:17:22");
                add("13:17:23");
                add("13:19:20");
                add("13:19:40");
                add("13:19:41");
                add("13:20:20");
                add("13:20:21");
                add("13:20:22");
            }
        }.stream().map(s -> {
            try {
                SpbAlert spbAlert = new SpbAlert();
                spbAlert.setDate(new SimpleDateFormat("dd/MM/yy hh:mm:ss").parse("01/04/19 " + s));
                return spbAlert;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        groupInfo.setAlertList(list);
        Double actual = groupInfo.getActual();

        RequestInfo requestInfo = new RequestInfo();
    }

}
