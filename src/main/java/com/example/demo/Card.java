package com.example.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Card {
	public static void main(String[] args) {
		String[] cardType = { "♠", "♥", "♣", "♦" };
		String[] numberArr = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };

		List<String> list = new ArrayList<String>();

		for (int i = 0; i < cardType.length; i++) {
			for (int j = 0; j < numberArr.length; j++) {
				list.add(cardType[i] + numberArr[j]);
			}
		}

		System.out.println("顺序打乱前：");
		System.out.println(list);

		Collections.shuffle(list);

		System.out.println("顺序打乱后：");
		System.out.println(list);
	}
}
