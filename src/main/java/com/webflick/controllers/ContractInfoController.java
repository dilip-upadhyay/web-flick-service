//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.webflick.controllers;

import com.webflick.models.ContractInfo;
import com.webflick.repositories.ContractInfoRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Generated;
import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(
    origins = {"*"}
)
@RestController
@RequestMapping(
    path = {"/contractinfo"}
)
@RequiredArgsConstructor
public class ContractInfoController {
    private final ContractInfoRepository repository;
    private final JdbcTemplate h2JdbcTemplate;

    @GetMapping
    public List<?> getAll() {
        String sql = "SELECT * FROM contractinfo";
        List<Map<String, Object>> resultList = new ArrayList<>();
        h2JdbcTemplate.query(sql, (rs) -> {
            Map<String, Object> rowMap = new HashMap<>();
            int columnCount = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                rowMap.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
            }
            resultList.add(rowMap);
        });
        return resultList;
    }

    @PostMapping
    public List<ContractInfo> add(@RequestBody List<ContractInfo> var1) {
        return this.repository.saveAll(var1);
    }


}
