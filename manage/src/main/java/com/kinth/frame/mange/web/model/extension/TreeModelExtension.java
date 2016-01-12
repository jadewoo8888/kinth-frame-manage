package com.kinth.frame.mange.web.model.extension;

import java.util.List;
import java.util.ArrayList;


import org.springframework.stereotype.Component;


public class TreeModelExtension {
	
	/*@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <EntityType extends ChainEntity<EntityType>> List<TreeModel> ToTreeModels(
			ChainEntityService chainEntityService, List<EntityType> entities,
			String selectedId, List<String> checkedIdList,
			List<String> expandedIdList, Class<?> chainEntityClazz) {

		List<TreeModel> treeModels = new ArrayList<TreeModel>();
		for (EntityType entity : entities) {
			boolean checked = false;
			boolean selected = false;
			boolean collpase = true;
			List<TreeModel> children = null;

			if (selectedId != null && selectedId.equals(entity.getId())) {
				selected = true;
			}
			if (checkedIdList != null && !checkedIdList.isEmpty()) {
				checked = checkedIdList.contains(entity.getId());
			}
			if (expandedIdList != null && !expandedIdList.isEmpty()) {
				collpase = !expandedIdList.contains(entity.getId());
			}

			List<ChainEntity> list = chainEntityService.getChildren(chainEntityClazz, entity.getId());
			if (list != null && list.size() > 0) {
				children = ToTreeModels(chainEntityService, list, selectedId,checkedIdList, expandedIdList, chainEntityClazz);
			}

			treeModels.add(new TreeModel(entity.getId(), entity.getId(), entity.getName(), checked,
					selected, collpase, children));

		}
		return treeModels;
	}*/
	
}
