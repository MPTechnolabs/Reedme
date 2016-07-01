package com.example.reedme.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jayes_000 on 28-Dec-15.
 */
public class FAQsModel  {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> first = new ArrayList<String>();
        first.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc sed dapibus orci, vel pharetra risus. Ut mollis, quam in condimentum commodo, tellus dolor tempor sem, efficitur tincidunt odio velit id est. Fusce et aliquam arcu.");

        List<String> second = new ArrayList<String>();
        second.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc sed dapibus orci, vel pharetra risus. Ut mollis, quam in condimentum commodo, tellus dolor tempor sem, efficitur tincidunt odio velit id est. Fusce et aliquam arcu.");

        List<String> third = new ArrayList<String>();
        third.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc sed dapibus orci, vel pharetra risus. Ut mollis, quam in condimentum commodo, tellus dolor tempor sem, efficitur tincidunt odio velit id est. Fusce et aliquam arcu.");

        List<String> fourth = new ArrayList<String>();
        fourth.add("Lorem ipsum dolor sit amet,");

        List<String> fifth = new ArrayList<String>();
        fifth.add("Lorem ipsum dolor sit amet,");

        List<String> sixth = new ArrayList<String>();
        sixth.add("Lorem ipsum dolor sit amet,");

        List<String> seventh = new ArrayList<String>();
        seventh.add("Lorem ipsum dolor sit amet,");

        List<String> eighth = new ArrayList<String>();
        eighth.add("Lorem ipsum dolor sit amet,");

        List<String> ninth = new ArrayList<String>();
        ninth.add("Lorem ipsum dolor sit amet,");

        List<String> tenth = new ArrayList<String>();
        tenth.add("Lorem ipsum dolor sit amet,");

        expandableListDetail.put("The standard Lorem Ipsum passage, used", first);
        expandableListDetail.put("Section 1.10.32 of de Finibus Bonorum et Malorum", second);
        expandableListDetail.put("1914 translation by H. Rackham", third);
        expandableListDetail.put("Written by Cicero in 45 BC", fourth);
        expandableListDetail.put("1914 translation by H. Rackham", fifth);
        expandableListDetail.put("The standard Lorem Ipsum passage, used", sixth);
        expandableListDetail.put("Section 1.10.32 of de Finibus Bonorum et Malorum", seventh);
        expandableListDetail.put("1914 translation by H. Rackham", eighth);
        expandableListDetail.put("Written by Cicero in 45 BC", ninth);
        expandableListDetail.put("1914 translation by H. Rackham", tenth);
        return expandableListDetail;
    }
}

