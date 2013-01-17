package liqp.filters;

import java.util.ArrayList;
import java.util.List;

class Map extends Filter {

    @Override
    public Object apply(Object value, Object... params) {

        if(value == null) {
            return "";
        }

        List<Object> list = new ArrayList<Object>();

        Object[] array = super.asArray(value);

        String key = super.asString(super.get(0, params));

        for(Object obj : array) {

            java.util.Map map = (java.util.Map)obj;

            Object val = map.get(key);

            if(val != null) {
                list.add(super.asString(val));
            }
        }

        return list.toArray(new Object[list.size()]);
    }
}