package ru.croc.totmrtest.util;

import static io.restassured.path.json.JsonPath.from;

public class GetIdHelper {
    public static Long getId(String obj){
        return Long.valueOf(from(obj).get("updatedObjects.id")
                .toString().replace("[", "").replace("]", ""));
    }
}
