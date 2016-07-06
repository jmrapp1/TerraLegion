package com.jmrapp.terralegion.game.utils;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Jon on 12/14/15.
 */
public class CachePool {

	private static final Array<Table> tableCache = new Array<Table>();
	private static final Array<Group> groupCache = new Array<Group>();

	public static void addTable(Table table) {
		tableCache.add(table);
	}

	public static void addGroup(Group group) {
		groupCache.add(group);
	}

	public static Table getTable() {
		if (tableCache.size > 0) {
			Table table = tableCache.removeIndex(0);
			table.clear();
			return table;
		}
		return new Table();
	}

	public static Group getGroup() {
		if (groupCache.size > 0) {
			Group group = groupCache.removeIndex(0);
			group.clear();
			return group;
		}
		return new Group();
	}

}
