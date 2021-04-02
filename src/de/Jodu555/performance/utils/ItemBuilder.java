package de.Jodu555.performance.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemBuilder {
	
	private ItemStack item;
	private ItemMeta meta;

	public ItemBuilder(Material m) {
		this(m, 1);
	}

	public ItemBuilder(ItemStack item) {
		this.item = item;
		meta = item.getItemMeta();
	}

	public ItemBuilder(Material m, int amount) {
		item = new ItemStack(m, amount);
		meta = item.getItemMeta();
	}

//	public ItemBuilder(Material m, int amount, byte subid) {
//		item = new ItemStack(m, amount, subid);
//		meta = item.getItemMeta();
//	}

	public ItemBuilder setName(String name) {
		meta.setDisplayName(name);
		return this;
	}

	public ItemBuilder setNoName() {
		meta.setDisplayName(" §a");
		return this;
	}

	public ItemBuilder setSkullOwner(String owner) {
//		String value = getHeadValue(owner);
//		UUID hashAsId = new UUID(value.hashCode(), value.hashCode());
//		item = Bukkit.getUnsafe().modifyItemStack(item,
//				"{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + value + "\"}]}}}"
//
//		);
		((SkullMeta) meta).setOwningPlayer(Bukkit.getOfflinePlayer(owner));
		return this;
	}
	
	public ItemBuilder setSkullOwnerUUID(String owneruuid) {
//		UUID owner = UUID.fromString(owneruuid);
//		String value = getHeadValue(owner);
//		System.out.println("setSkullOwnerUUID: " + value);
//		UUID hashAsId = new UUID(value.hashCode(), value.hashCode());
//		System.out.println("setSkullOwnerUUID: " + hashAsId);
//		item = Bukkit.getUnsafe().modifyItemStack(item,
//				"{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + value + "\"}]}}}"
//
//		);
		
		((SkullMeta) meta).setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString(owneruuid)));
		return this;
	}

	public ItemBuilder setLore(String... lore) {
		this.setLore(Arrays.asList(lore));
		return this;
	}

	public ItemBuilder setLore(List<String> lore) {
		List<String> finallore = new ArrayList<>();
		lore.forEach(loreitems -> {
			finallore.add("§7" + loreitems);
		});
		meta.setLore(finallore);
		return this;
	}

	public List<String> getLore() {
		return meta.getLore();
	}

	public ItemBuilder addLore(String lore) {
		List<String> oldlore = getLore();
		oldlore.add("§7" + lore);
		meta.setLore(oldlore);
		return this;
	}

	public ItemBuilder removeLore(int index) {
		List<String> oldlore = getLore();
		oldlore.remove(index);
		meta.setLore(oldlore);
		return this;
	}

	public ItemBuilder removeLore(String lore) {
		List<String> oldlore = getLore();
		oldlore.remove(lore);
		meta.setLore(oldlore);
		return this;
	}

	public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
		meta.addEnchant(enchantment, level, true);
		return this;
	}

	public ItemBuilder removeAllFlags() {
		meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
		meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_DESTROYS });
		meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_UNBREAKABLE });
		meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
		meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_PLACED_ON });
		meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_POTION_EFFECTS });
		return this;
	}

	public ItemBuilder setGlow() {
		meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		return this;
	}

	public ItemStack build() {
		item.setItemMeta(meta);
		return item;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public ItemBuilder clone() {
		return new ItemBuilder(this.build());

	}
	
//	private String getHeadValue(UUID uuid) {
//		String uid = "";
//		if(uuid.toString().contains("-")) {
//			uid = uuid.toString().replaceAll("-", "");
//		} else {
//			uid = uuid.toString();
//		}
//		try {
//			Gson g = new Gson();
//			String signature = getURLContent("https://sessionserver.mojang.com/session/minecraft/profile/" + uid);
//	        JsonObject obj = g.fromJson(signature, JsonObject.class);
//	        String value = obj.getAsJsonArray("properties").get(0).getAsJsonObject().get("value").getAsString();
//	        String decoded = new String(Base64.getDecoder().decode(value));
//	        System.out.println(decoded);
//	        obj = g.fromJson(decoded,JsonObject.class);
//	        String skinURL = obj.getAsJsonObject("textures").getAsJsonObject("SKIN").get("url").getAsString();
//	        byte[] skinByte = ("{\"textures\":{\"SKIN\":{\"url\":\"" + skinURL + "\"}}}").getBytes();
//	        return new String(Base64.getEncoder().encode(skinByte));
//		} catch (Exception ignored) { }
//		return null;
//	}
//	
//	private String getHeadValue(String name) {
//		try {
//	        String result = getURLContent("https://api.mojang.com/users/profiles/minecraft/" + name);
//	        Gson g = new Gson();
//	        JsonObject obj = g.fromJson(result, JsonObject.class);
//	        String uid = obj.get("id").toString().replace("\"","");
//	        String signature = getURLContent("https://sessionserver.mojang.com/session/minecraft/profile/" + uid);
//	        obj = g.fromJson(signature, JsonObject.class);
//	        String value = obj.getAsJsonArray("properties").get(0).getAsJsonObject().get("value").getAsString();
//	        String decoded = new String(Base64.getDecoder().decode(value));
//	        obj = g.fromJson(decoded,JsonObject.class);
//	        String skinURL = obj.getAsJsonObject("textures").getAsJsonObject("SKIN").get("url").getAsString();
//	        byte[] skinByte = ("{\"textures\":{\"SKIN\":{\"url\":\"" + skinURL + "\"}}}").getBytes();
//	        return new String(Base64.getEncoder().encode(skinByte));
//	    } catch (Exception ignored){ }
//	    return null;
//	}
//	
//	private String getURLContent(String urlStr) {
//        URL url;
//        BufferedReader in = null;
//        StringBuilder sb = new StringBuilder();
//        try{
//            url = new URL(urlStr);
//            in = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8) );
//            String str;
//            while((str = in.readLine()) != null) {
//                sb.append( str );
//            }
//        } catch (Exception ignored) { }
//        finally{
//            try{
//                if(in!=null) {
//                    in.close();
//                }
//            }catch(IOException ignored) { }
//        }
//        return sb.toString();
//    }
//	
//	private String subString(String str, String strStart, String strEnd) {
//	    int strStartIndex = str.indexOf(strStart);
//	    int strEndIndex = str.indexOf(strEnd);
//	    return str.substring(strStartIndex, strEndIndex).substring(strStart.length());
//	}
	
}
