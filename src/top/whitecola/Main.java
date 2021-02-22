package top.whitecola;

import com.google.gson.*;
import top.whitecola.jsons.*;
import top.whitecola.utils.HiURL;
import top.whitecola.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicReference;


public class Main {
    public static Gson gson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().setLenient().create();
    public static String id = "White_colao1";
    public static String uuid = "a3f48db89fee47ccb229f3d8af14d27a";
    public static String skin = "http://textures.minecraft.net/texture/602ccd3a803ef390f3628f789ba58179596975f6d34bcea9aad0403a26bf2a3f";
    public static String cape = "";
    public static void main(String[] args) throws Throwable{
        if(args.length==1){
            id = args[0];
        }
        String uuidRequest = HiURL.readURL("https://api.mojang.com/users/profiles/minecraft/"+id+"?at="+System.currentTimeMillis());
//        System.out.println(uuidRequest);
        if(uuidRequest.contains("IllegalArgumentException")){
            System.out.println("Cannot get UUID because that UUID does not exist! (1)");
            return;
        }
        try {
            UUID uuidObject = gson.fromJson(uuidRequest, UUID.class);
            uuid = uuidObject.getId();
        }catch (NullPointerException e){
            System.out.println("Cannot get UUID because UUID does not exist! (2)");
//            System.out.println("["+uuidRequest+"]");
            return;
        }
        String profileRequest = HiURL.readURL("https://sessionserver.mojang.com/session/minecraft/profile/"+uuid);

        Profile profile = gson.fromJson(profileRequest,Profile.class);

//        System.out.println(profileRequest);

        AtomicReference<String> value = new AtomicReference<>("");
        profile.getProperties().forEach(pro->{
            value.set(pro.getValue());
        });

        String trueProfileRequest = new String(Base64.getDecoder().decode(value.get()));
//        System.out.println(trueProfileRequest);

        if(!trueProfileRequest.contains("cape")){
            TrueProfileNoCape trueProfileNoCape = gson.fromJson(trueProfileRequest,TrueProfileNoCape.class);
            skin = trueProfileNoCape.getTextures().getSKIN().getUrl();
        }else{
            TrueProfileCape trueProfileCape = gson.fromJson(trueProfileRequest,TrueProfileCape.class);
            skin = trueProfileCape.getTextures().getSKIN().getUrl();
            cape = trueProfileCape.getTextures().getCAPE().getUrl();
        }

        String historyRequest = HiURL.readURL("https://api.mojang.com/user/profiles/"+uuid+"/names");

        ArrayList<ChangeName> changeNameList = new ArrayList<>();

        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(historyRequest).getAsJsonArray();
        for (JsonElement user : jsonArray) {
            ChangeName changeName = gson.fromJson(user, ChangeName.class);
            changeNameList.add(changeName);
        }


        System.out.println("Player ID: "+id);
        System.out.println("Player UUID: "+uuid);
        System.out.println("Skin: "+skin);
        if(!cape.equals("")){
            System.out.println("Cape: "+cape);
        }else{
            System.out.println("Cape: noCape");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("History names: "+"\n");
        sb.append("["+"\n");
        changeNameList.forEach(i->{
            sb.append(i.getName());
            if(i.getChangedToAt()!=-1){
                sb.append(" - ");
                sb.append(TimeUtil.timestampToDateStr(i.getChangedToAt()));
            }
            sb.append("\n");
        });
        sb.append("]");

        System.out.println(sb.toString());
//        System.out.println("<<<<MCping by White_cola>>>>");
    }

}
