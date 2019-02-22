package com.shang.fcu_food

import com.google.firebase.FirebaseApp
import com.shang.fcu_food.Data.*
import com.shang.fcu_food.Data.Temp.TempData
import com.shang.fcu_food.Data.Temp.TempMenu
import com.shang.fcu_food.Data.Temp.TempShop
import com.shang.fcu_food.Data.menu.*
import com.shang.fcu_food.Data.shop.*
import com.shang.fcu_food.Main.MainActivity
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test


class DataTest {
    @Test
    fun Shop_Test() {
        var breakfastShop = BreakfastShop()
        var dinnerShop = DinnerShop()
        var drinkShop = DrinkShop()
        var snackShop = SnackShop()

        assertTrue(breakfastShop is Shop)
        assertTrue(dinnerShop is Shop)
        assertTrue(drinkShop is Shop)
        assertTrue(snackShop is Shop)

        Assert.assertEquals(breakfastShop.errorDrawable, R.drawable.ic_shop)
        Assert.assertEquals(dinnerShop.errorDrawable, R.drawable.ic_shop)
        Assert.assertEquals(drinkShop.errorDrawable, R.drawable.ic_shop)
        Assert.assertEquals(snackShop.errorDrawable, R.drawable.ic_shop)

        assertEquals("breakfast", Shop.BREAKFAST_SHOP)
        assertEquals("dinner", Shop.DINNER_SHOP)
        assertEquals("drink", Shop.DRINK_SHOP)
        assertEquals("snack", Shop.SNACK_SHOP)
    }

    @Test
    fun DataConstant_Check() {
        assertEquals("POSITION", DataConstant.POSITION)
        assertEquals("SHOP_TYPE_TAG", DataConstant.SHOP_TYPE_TAG)
        assertEquals("SHOP_ID", DataConstant.SHOP_ID)
        assertEquals("SHOP_NAME", DataConstant.SHOP_NAME)
    }

    @Test
    fun Menu_TEST() {
        var menu = Menu()
        Assert.assertEquals("0.0", menu.star.toString()) //用浮點數比較會有不準的問題
        menu.usercomment = mutableListOf<UserComment>().apply {
            this.add(UserComment("uid", "comment", "1"))
            this.add(UserComment("uid", "comment", "2"))
            this.add(UserComment("uid", "comment", "3"))
            this.add(UserComment("uid", "comment", "4"))
            this.add(UserComment("uid", "comment", "5"))
        }
        Assert.assertEquals("3.0", menu.star.toString())

        var breakfastMenu = BreakfastMenu()
        var dinnerMenu = DinnerMenu()
        var drinkMenu = DrinkMenu()
        var snackMenu = SnackMenu()

        Assert.assertEquals(breakfastMenu.errorDrawable, R.drawable.ic_breakfast)
        Assert.assertEquals(dinnerMenu.errorDrawable, R.drawable.ic_dinner)
        Assert.assertEquals(drinkMenu.errorDrawable, R.drawable.ic_drink)
        Assert.assertEquals(snackMenu.errorDrawable, R.drawable.ic_snack)

        Assert.assertTrue(breakfastMenu is Menu)
        Assert.assertTrue(dinnerMenu is Menu)
        Assert.assertTrue(drinkMenu is Menu)
        Assert.assertTrue(snackMenu is Menu)
    }

    @Test
    fun User_Test() {
        var user = User("uid", "name", "1", "man")
        var map = user.toMap()
        Assert.assertEquals("name", map.get("name"))
        Assert.assertEquals("1", map.get("picture"))
        Assert.assertEquals("man", map.get("sex"))

        var flag = true
        for (i in 1..100) {
            var p = user.randomPicture()
            if (!p.equals("1") && !p.equals("2") && !p.equals("3") && !p.equals("4")) {
                flag = false
            }
        }
        Assert.assertTrue(flag)
    }

    @Test
    fun UserComment_Test() {
        var userComment = UserComment("uid", "comment", "1")
        var map = userComment.toMap()
        Assert.assertEquals("uid", map.get("uid"))
        Assert.assertEquals("1.0", map.get("star").toString())
        Assert.assertEquals("comment", map.get("comment"))

        //假設沒有取道user的資料
        Assert.assertEquals("小明", userComment.getUserName("uid"))
        Assert.assertEquals(R.drawable.ic_user, userComment.getUserPicture("uid"))
    }

    @Test
    fun TempData_Test(){
        var tempShop=TempShop()
        var tempMenu= TempMenu()
        Assert.assertTrue(tempMenu is TempData)
        Assert.assertTrue(tempShop is TempData)
    }

}