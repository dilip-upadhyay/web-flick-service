//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.webflick.repositories;

import com.webflick.models.ContractInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("contractInfoRepository")
public interface ContractInfoRepository extends JpaRepository<ContractInfo, Long> {
}
