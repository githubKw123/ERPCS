package net.kingborn.erp.service;

import net.kingborn.core.entry.IdGenerator;

public class IdGeneratorTest {

    public static void main(String[] args) {
        System.out.println(IdGenerator.getInstance().nextStringId());
    }

}
