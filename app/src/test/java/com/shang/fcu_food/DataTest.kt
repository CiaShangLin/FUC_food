package com.shang.fcu_food

import com.shang.fcu_food.Data.*
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test


class DataTest {
    @Test
    fun shopExtends() {
        var breakfastShop = BreakfastShop()
        var dinnerShop = DinnerShop()
        var drinkShop = DrinkShop()
        var snackShop = SnackShop()

        assertTrue(breakfastShop is Shop)
        assertTrue(dinnerShop is Shop)
        assertTrue(drinkShop is Shop)
        assertTrue(snackShop is Shop)


    }

    @Test
    fun shopTag_Check() {
        assertEquals("breakfast", BreakfastShop.tag)
        assertEquals("dinner", DinnerShop.tag)
        assertEquals("drink", DrinkShop.tag)
        assertEquals("snack", SnackShop.tag)
    }

    @Test
    fun DataConstant_Check() {
        assertEquals("POSITION", DataConstant.POSITION)
        assertEquals("SHOP_TYPE_TAG", DataConstant.SHOP_TYPE_TAG)
        assertEquals("SHOP_ID", DataConstant.SHOP_ID)
        assertEquals("SHOP_NAME", DataConstant.SHOP_NAME)
    }

    @Test
    fun Menu_Check() {
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
    }


}