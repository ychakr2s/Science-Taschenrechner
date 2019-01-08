package com.ybouhaki.mytesttaschenrechner32

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_modulo.*
import java.lang.Exception
import java.text.DecimalFormat
import kotlin.math.sign

class Modulo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modulo)

        // ActionBar
        var actionbar = supportActionBar
        // Set actionabar title
        actionbar!!.title= "Standard Rechner"
        // Set back Button
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    /*
        TextView present the result
        EditText present the entry
    */

    var arrayElement = ArrayList<String>()
    var myOp = ArrayList<String>()
    val df = DecimalFormat("###.######")
    var isNewChoice= true
    var isNewOpChoice=false

    fun fillOp():ArrayList<String>{
        myOp.addAll(listOf( "/", "%", "*","^"))
        return this.myOp
    }
    //++++++++++++++++++++++++++

    // The User choose a number
    fun btnNumEvent(view: View){
        if (isNewChoice){
            txtViewEntireNumber.text = ""
            isNewChoice=false
            isNewOpChoice= false
            arrayElement.clear()
        }
        var entryNumber= txtViewEntireNumber.text.toString()
        var btnSelected= view as Button

        when(btnSelected.id){
            btnNine.id-> {
                entryNumber= "9"
            }
            btnEight.id->{
                entryNumber= "8"
            }
            btnSeven.id ->{
                entryNumber= "7"
            }
            btnSechs.id->{
                entryNumber= "6"
            }
            btnFive.id->{
                entryNumber= "5"
            }
            btnFour.id->{
                entryNumber= "4"
            }
            btnThree.id->{
                entryNumber= "3"
            }
            btnTwo.id->{
                entryNumber= "2"
            }
            btnOne.id->{
                entryNumber= "1"
            }
            btnNull.id->{
                entryNumber= "0"
            }
            btnComma.id->{
                entryNumber= "."
            }
        }
        arrayElement.add(entryNumber)
        txtViewEntireNumber.text= readFromArray(arrayElement)
    }
    fun readFromArray(arr:ArrayList<String>):String{
        var retur=""
        for (i in arr){
            retur+=i
        }
        return retur
    }

    // Select an operation
    fun btnOpEvent(view: View){
        if (isNewOpChoice) {
            isNewOpChoice = false
            isNewChoice = false
            //arrayElement.clear()
        }
        var op=""

        val btnSelected = view as Button
        when(btnSelected.id){
            btnPlus.id->{
                op="+"
            }
            btnMinus.id->{
                op="-"
            }
            btnMultiple.id->{
                op="*"
            }
            btnDivision.id->{
                op="/"
            }
            btnModulo.id->{
                op="%"
            }
            btnXhochY.id->{
                op="^"
            }
        }

        arrayElement.add(op)
        txtViewEntireNumber.text= readFromArray(arrayElement)
    }

    fun arrayReduction(arr: ArrayList<String>): ArrayList<String> {
        var firstTerm:String = "";
        secondReduction(arr)
        var reductArray = ArrayList<String>()

        for ( i in 0.. arr.size-1 ){
            if((i==0 && arr[i]=="-") || ( i==0 && arr[i] == "+")  ){
                firstTerm+=arr[i]
            }else if(isInteger(arr[i]) || arr[i]== "."){
                firstTerm+=arr[i]
            }else {
                if(firstTerm != ""){
                    reductArray.add(firstTerm)
                }
                reductArray.add(arr[i])
                firstTerm = ""
            }
        }
        if(firstTerm!=""){ reductArray.add(firstTerm) }
        //secondReduction(reductArray)
        thirdReduction(reductArray)
        return reductArray
    }
    fun secondReduction(arr: ArrayList<String>): ArrayList<String> {
        var haveToRemove= ArrayList<Int>()
        var sign = ""; var sign2 = ""
        var a=1
        for (i in 0..arr.size-2){
            if( (arr[i]== "+" && arr[i+1] == "+" ) || ( arr[i] == "+" && arr[i+1] == "-" )||
                    (arr[i]== "-" && arr[i+1] == "+" ) || ( arr[i] == "-" && arr[i+1] == "-" )
            ) {
                sign = arr[i]+1; sign2 = arr[i+1]+1;
                a= Integer.parseInt(sign) * Integer.parseInt(sign2)
                arr[i+1]= siggn(a)

                haveToRemove.add(i)
            }

        }

        for (i in 0.. haveToRemove.size-1){
            arr.removeAt(haveToRemove.get(i)-i)
        }
        return arr
    }
    fun thirdReduction(arr: ArrayList<String>): ArrayList<String> {
        fillOp();
        var haveToRemove= ArrayList<Int>()
        for (i in 1..arr.size-2){
            if((myOp.contains( arr[i-1] ) && arr[i]== "-" && isDouble(arr[i+1])) ||
                    (  myOp.contains( arr[i-1] )&& arr[i]== "+" && isInteger(arr[i+1]) ) ){
                arr[i]= arr[i]+ arr[i+1]
                haveToRemove.add(i+1)
            }
        }
        for (i in 0.. haveToRemove.size-1){
            arr.removeAt(haveToRemove.get(i)-i)
        }
        return arr
    }
    fun fourthReduction(arr: ArrayList<String>): ArrayList<String> {
        var result:Double
        var haveToRemove= ArrayList<Int>()
        for (i in 0..arr.size-1){
            if(checkNext(i,arr)){
                if(isDouble(arr[i]) && isDouble(arr[i+1])){
                    result= arr[i].toDouble()*arr[i+1].toDouble()
                    arr[i+1]= result.toString()
                    haveToRemove.add(i)
                }
            }
        }
        for (i in 0.. haveToRemove.size-1){
            arr.removeAt(haveToRemove.get(i)-i)
        }
        return arr
    }

    fun checkNext(i:Int,arr: ArrayList<String>):Boolean{
        return i+1<=arr.size-1
    }
    fun isDouble(s: String):Boolean{
        try {
            s.toDouble()
            return true
        }catch (ex: Exception){
            return false
        }
    }

    fun siggn(num:Int):String{
        return if (sign(num.toDouble()) <0){
            "-"
        }else  "+"
    }

    fun isInteger(s:String):Boolean{
        try {
            Integer.parseInt(s)
            return true
        }catch (e: Exception){
            return false
        }
        return true
    }
    fun checkForNextPrevious(i:Int, arr: java.util.ArrayList<String>):Boolean{
        return i+1<arr.size && i-1>=0
    }
    fun isValid(arr: ArrayList<String>):Boolean{
        if(arr.size>0) {
            if (isDouble(arr[0]) && isDouble(arr[arr.size - 1])) {
                for (i in 0..arr.size - 1) {
                    when (arr.get(i)) {
                        "+" -> {
                            if (!isDouble(arr[i - 1]) || !isDouble(arr[i + 1])) {
                                return false
                            }
                        }
                        "-" -> {
                            if (!isDouble(arr[i - 1]) || !isDouble(arr[i + 1])) {
                                return false
                            }
                        }
                        "*" -> {
                            if (!isDouble(arr[i - 1]) || !isDouble(arr[i + 1])) {
                                return false
                            }
                        }
                        "/" -> {
                            if (!isDouble(arr[i - 1]) || !isDouble(arr[i + 1])) {
                                return false
                            }
                        }
                        "%" -> {
                            if (!isDouble(arr[i - 1]) || !isDouble(arr[i + 1])) {
                                return false
                            }
                        }
                    }

                }
            } else
                return false
        }
        return true
    }

    fun addition (a:Double, b: Double):Double{
        var result = df.format(a+b)
        result = result.replace(",",".")
        return result.toDouble()
    }
    fun subtraction(a:Double, b: Double):Double{
        var result = df.format(a-b)
        result = result.replace(",",".")
        return result.toDouble()
    }
    fun multiplication(a:Double, b: Double):Double{
        var result = df.format(a*b)
        result = result.replace(",",".")
        return result.toDouble()
    }
    fun modulo(a:Double, b: Double):Double{
        var result = df.format(a%b)
        result = result.replace(",",".")
        return result.toDouble()
    }
    fun pow(x: Double, y: Double): Double {
        return if (y >= 0) {
            if (y == 0.0)
                1.0
            else
                x * pow(x, y - 1)
        } else{
            var res = df.format(1 / (x * pow(x, -y - 1)))
            res = res.replace(",", ".")
            return res.toDouble()
        }
    }
    fun callEnd(arr: ArrayList<String>): ArrayList<String> {
        var result:Double
        var elementToRemove = ArrayList<Int>()

        for (i in 0..arr.size-1){
            when(arr.get(i)){
                "+"->{
                    result = addition(arr.get(i-1).toDouble(),arr.get(i+1).toDouble())
                    arr.set(i+1,result.toString())
                    elementToRemove.add(i-1); elementToRemove.add(i)
                }
                "-"->{
                    result = subtraction(arr.get(i-1).toDouble(),arr.get(i+1).toDouble())
                    arr.set(i+1,result.toString())
                    elementToRemove.add(i-1); elementToRemove.add(i)
                }
            }
        }
        for (i in 0.. elementToRemove.size-1){
            arr.removeAt(elementToRemove.get(i)-i)
        }
        return arr
    }
    fun safeDivide(dividend: Double, divisor: Double): Double {
        if (java.lang.Double.compare(divisor, Double.NaN) == 0) return Double.NaN
        if (java.lang.Double.compare(dividend, Double.NaN) == 0) return Double.NaN
        if (java.lang.Double.compare(divisor, 0.0) == 0) {
            return if (java.lang.Double.compare(dividend, 0.0) == -1) {
                Double.NEGATIVE_INFINITY
            } else Double.POSITIVE_INFINITY
        }
        return if (java.lang.Double.compare(divisor, -0.0) == 0) {
            if (java.lang.Double.compare(dividend, -0.0) == 1) {
                Double.NEGATIVE_INFINITY
            } else Double.POSITIVE_INFINITY //FFG
        } else{
            var result= df.format(dividend / divisor)
            result = result.replace(",",".")
            return result.toDouble()
        }
    }
    fun pi():Double{
        var p = df.format(Math.PI)
        p=p.replace(",",".")
        return p.toDouble()
    }

    fun btnnOpEqual(view: View){
        var readyOp= arrayReduction(arrayElement)
        var result:Double
        var haveToRemove = ArrayList<Int>()
        // I must do that with Iterator. I have a method with int i = it.nextIndex();
        for(i in 0..readyOp.size-1){
            // ++++++++++++++++++++++++++++++++++++ op +++++++++++++++++++++++++++++++++++
            if(checkForNextPrevious(i,readyOp)) {
                if (isDouble(readyOp[i + 1]) && isDouble(readyOp[i - 1])) {
                    when ( readyOp[i] ) {
                        "*" -> {
                            result = multiplication(readyOp[i - 1].toDouble(), readyOp[i + 1].toDouble())
                            readyOp[i + 1] = result.toString()
                            haveToRemove.add(i - 1); haveToRemove.add(i)
                        }
                        "/" -> {
                            result = safeDivide(readyOp[i - 1].toDouble(), readyOp[i + 1].toDouble())
                            readyOp[i + 1] = result.toString()
                            haveToRemove.add(i - 1); haveToRemove.add(i)
                        }
                        "%" -> {
                            result = modulo(readyOp[i - 1].toDouble(), readyOp[i + 1].toDouble())
                            readyOp[i + 1] = result.toString()
                            haveToRemove.add(i - 1); haveToRemove.add(i)
                        }
                        "^" ->{
                            result = pow(readyOp[i - 1].toDouble(), readyOp[i + 1].toDouble())
                            readyOp[i + 1] = result.toString()
                            haveToRemove.add(i - 1);haveToRemove.add(i)
                        }
                    }
                }
            }

        }

        for (i in 0.. haveToRemove.size-1) {
            readyOp.removeAt(haveToRemove.get(i)-i)
        }
        if(isValid(readyOp)) {
            fourthReduction(readyOp)
            if (readyOp.contains( "Infinity" ) || readyOp.contains( "-Infinity" ) || readyOp.contains( "NaN" ) ){
                txtViewShowResult.text = "INVALID INPUT"
            }else
            {
                callEnd(readyOp)
                txtViewShowResult.text= readFromArray(readyOp)
                arrayElement.clear()
                arrayElement.addAll(readyOp)

            }
        }
        else{
            txtViewShowResult.text = "Syntax Error"
        }
        isNewChoice= true
        isNewOpChoice= true
    }
    fun btnClear(view: View){
        txtViewShowResult.text=""
        txtViewEntireNumber.text=""
        arrayElement.clear()
    }
    fun btOpDelete(view: View){
        if (arrayElement.size==0){
            txtViewEntireNumber.text=""
        }else{
            arrayElement.removeAt(arrayElement.size - 1)
            isNewChoice= false
            isNewOpChoice= false
            txtViewEntireNumber.text= readFromArray(arrayElement)
        }
    }
}
