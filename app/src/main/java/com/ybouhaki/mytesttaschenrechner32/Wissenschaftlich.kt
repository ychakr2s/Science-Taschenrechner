package com.ybouhaki.mytesttaschenrechner32

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_wissenschaftlich.*
import java.lang.Exception
import java.lang.NumberFormatException
import java.text.DecimalFormat
import kotlin.math.*


class Wissenschaftlich : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wissenschaftlich)

        // ActionBar
        var actionbar = supportActionBar
        // Set actionabar title
        actionbar!!.title= "Wissenschaftlich"
        // Set back Button
        actionbar.setDisplayHomeAsUpEnabled(true)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    var isNewChoice= true
    var isOpChoice = false
    var isNewOpFunCoice =true
    var arrElement = ArrayList<String>()
    var myOp= ArrayList<String>()

    // fill the Array of Operations.
    fun fillOp():ArrayList<String>{
        myOp.addAll(listOf("ln", "log", "sin", "cos", "acos", "asin", "atan", "tang", "ex", "√", "cot", "10^", "^", "/", "%", "*"))
        return myOp
    }

    // here will read from array and print  it in TextView
    fun readFromArray(arr:ArrayList<String>):String {
        var retur=""
        for (i in arr){
            retur+=i
        }
        return retur
    }

    // The User choose a number
    fun btNumEvent(view: View){
        if (isNewChoice){
            txtViewEntry.text = ""
            arrElement.clear()
            isNewChoice=false
            isOpChoice= false
            isNewOpFunCoice = false
        }
        var entryNumber= txtViewEntry.text.toString()
        var btnSelected= view as Button

        when(btnSelected.id){
            btn9.id-> {
                entryNumber= "9"
            }
            btn8.id->{
                entryNumber= "8"
            }
            btn7.id ->{
                entryNumber= "7"
            }
            btn6.id->{
                entryNumber= "6"
            }
            btn5.id->{
                entryNumber= "5"
            }
            btn4.id->{
                entryNumber= "4"
            }
            btn3.id->{
                entryNumber= "3"
            }
            btn2.id->{
                entryNumber= "2"
            }
            btn1.id->{
                entryNumber= "1"
            }
            btn0.id->{
                entryNumber= "0"
            }
            btncom.id->{
                entryNumber= "."
            }
        }
        arrElement.add(entryNumber)
        txtViewEntry.text = readFromArray(arrElement)
    }

    // Select an operation
    fun btOpEvent(view: View) {
        if (isOpChoice) {
            // txtViewEntry.text = ""
            isOpChoice = false
            isNewChoice = false
            isNewOpFunCoice= false
            //   arrElement.clear()
        }
        var op = ""
        val btnSelected = view as Button
        when (btnSelected.id) {
            btnPl.id -> {
                op = "+"
            }
            btnMin.id -> {
                op = "-"
            }
            btnMult.id -> {
                op = "*"
            }
            btnDiv.id -> {
                op = "/"
            }
            btnMod.id -> {
                op = "%"
            }
        }
        arrElement.add(op)
        txtViewEntry.text = readFromArray(arrElement)
    }
    fun btOpfunc(view: View){
        if (isNewOpFunCoice){
            txtViewEntry.text = ""
            arrElement.clear()
            isOpChoice=false
            isNewOpFunCoice =  false
            isNewChoice=false
        }
        var op = ""
        val btnSelected = view as Button
        when(btnSelected.id){
            btnNegPos.id->{
                op="-"
            }
            btnThX.id->{
                op="10^"
            }
            btnXhY.id->{
                op="^"
            }
            btnNfact.id->{
                op="!"
            }
            btnRoot.id->{
                op="√"
            }
            btnPi.id->{
                op="π"
            }
            btnLn.id->{
                op="ln"
            }
            btnLog.id->{
                op="log"
            }
            btnExpn.id->{
                op="ex"
            }
            btnEp.id->{
                op="e"
            }
            btnInvTang.id->{
                op="atan"
            }
            btnTang.id->{
                op="tang"
            }
            btnCos.id->{
                op="cos"
            }
            btnInvCos.id->{
                op="acos"
            }
            btnSin.id->{
                op="sin"
            }
            btnInvSin.id->{
                op="asin"
            }
        }
        arrElement.add(op)
        txtViewEntry.text=readFromArray(arrElement)
    }

    /*
     First of all, i make the Array suitable for Calculation.
     I put the Numbers with each other in the first ArrayReduction.
     In the second ArrayReduction i put the minus in the suitable place. e.g with a Number.
     */
    fun arrayReduction(arr: ArrayList<String>): ArrayList<String> {
        var firstTerm:String = "";
        secondReduction(arr)
        var reductArray = ArrayList<String>()

        for ( i in 0.. arr.size-1 ){
            if((i==0 && arr[i]=="-") || (i==0 && arr[i]=="+") ){
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
        // secondReduction(reductArray)
        thirdReduction(reductArray)
        return reductArray
    }

    fun secondReduction (arr: ArrayList<String>): ArrayList<String> {
        var haveToRemove= ArrayList<Int>()
        var sign=""; var sign2=""; var a=1

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
        for (i in 0..arr.size){
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

    fun siggn(num:Int):String{
        return if (sign(num.toDouble()) <0){
            "-"
        }else  "+"
    }

    // Check if a String is integer and Double
    fun isInteger(s:String):Boolean{
        try {
            Integer.parseInt(s)
            return true
        }catch (ex: Exception){
            return false
        }
    }
    fun isDouble(s: String):Boolean{
        try {
            s.toDouble()
            return true
        }catch (ex: NumberFormatException){
            return false
        }
    }
    fun checkPrevious(i:Int, arr: ArrayList<String>):Boolean{
        return i-1>=0
    }
    // This Method checks if an Element has Next Element or Not
    fun checkNext(i:Int,arr: ArrayList<String>):Boolean{
        return i+1<=arr.size-1
    }
    // This Method checks if an Element has has Previous and Next
    fun checkForNextPrevious(i:Int, arr: ArrayList<String>):Boolean{
        return i+1<arr.size && i-1>=0
    }
    // This Method check if a SIGN is valid to make Operation and ready for Operation
    fun isValid(arr:ArrayList<String>):Boolean{
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
    // the User want to see the last Result
    // here i will calculate the high priority Operations

    val df = DecimalFormat("###.########")

    fun btOpEqu(view: View){

        var readyForOp= arrayReduction(arrElement)
        var result:Double
        var haveToRemove = ArrayList<Int>()

        for(i in 0..readyForOp.size-1){
            if(checkNext(i,readyForOp)){

                if(isDouble(readyForOp[i+1])) {
                    when (readyForOp[i]) {
                        "√" -> {
                            result = squareRoot(readyForOp[i + 1].toDouble())
                            readyForOp.set(i + 1, result.toString())
                            haveToRemove.add(i)
                        }
                        "10^" -> {
                            result = tenHx(readyForOp[i + 1].toInt())
                            readyForOp[i + 1] = result.toString()
                            haveToRemove.add(i)
                        }
                        "ln" -> {
                            result = logn(readyForOp[i + 1].toDouble())
                            readyForOp[i + 1] = result.toString()
                            haveToRemove.add(i)
                        }
                        "log" -> {
                            result = logTen(readyForOp[i + 1].toDouble())
                            readyForOp[i + 1] = result.toString()
                            haveToRemove.add(i)
                        }
                        "cos" -> {
                            result = cosinus(readyForOp[i + 1].toDouble())
                            readyForOp[i + 1] = result.toString()
                            haveToRemove.add(i)
                        }
                        "sin" -> {
                            result = sinus(readyForOp[i + 1].toDouble())
                            readyForOp[i + 1] = result.toString()
                            haveToRemove.add(i)
                        }
                        "asin" -> {
                            result = inverseSinus(readyForOp[i + 1].toDouble()).toDouble()
                            readyForOp[i + 1] = result.toString()
                            haveToRemove.add(i)
                        }
                        "acos" -> {
                            result = inverseCosinus(readyForOp[i + 1].toDouble()).toDouble()
                            readyForOp[i + 1] = result.toString()
                            haveToRemove.add(i)
                        }
                        "tang" -> {
                            result = tangent(readyForOp[i + 1].toDouble()).toDouble()
                            readyForOp[i + 1] = result.toString()
                            haveToRemove.add(i)
                        }
                        "atan" -> {
                            result = arcTang(readyForOp[i + 1].toDouble())
                            readyForOp[i + 1] = result.toString()
                            haveToRemove.add(i)
                        }
                        "ex" -> {
                            result = ex(readyForOp[i + 1].toDouble())
                            readyForOp[i + 1] = result.toString()
                            haveToRemove.add(i);
                        }
                    }
                }
            }
            if(readyForOp[i]=="π" || readyForOp[i]=="e"){
                when(readyForOp[i]){
                    "π" -> {
                        result = pi()
                        readyForOp[i] = result.toString()
                    }

                    "e" -> {
                        result = ex(1.0)
                        readyForOp[i] = result.toString()
                    }
                }
            }
            if(readyForOp[i]=="^" && checkForNextPrevious(i,readyForOp)){
                if(isDouble(readyForOp[i+1]) && isDouble(readyForOp[i-1])){
                    result = pow(readyForOp[i - 1].toDouble(), readyForOp[i + 1].toDouble())
                    readyForOp[i + 1] = result.toString()
                    haveToRemove.add(i - 1);haveToRemove.add(i)
                }
            }
            if (readyForOp[i]=="!" && checkPrevious(i,readyForOp)){
                if(isDouble(readyForOp.get(i-1))){
                    result = factorial(readyForOp[i - 1].toDouble())
                    readyForOp[i] = result.toString()
                    haveToRemove.add(i - 1)
                }
            }
        }
        for (i in 0.. haveToRemove.size-1){
            readyForOp.removeAt(haveToRemove.get(i)-i)
        }
        if(isValid(readyForOp)){
            fourthReduction(readyForOp)
            calMul(readyForOp)
            calDivision(readyForOp)
            calMod(readyForOp)
            if (readyForOp.contains("Infinity") || readyForOp.contains("-Infinity") || readyForOp.contains("NaN") ){
                txtViewResult.text = "INVALID INPUT"
            }else
            {
                callEnd(readyForOp)
                txtViewResult.text= readFromArray(readyForOp)
                arrElement.clear()
                arrElement.addAll(readyForOp)
            }
        }
        else{
            txtViewResult.text = "Syntax Error"
        }
        isNewChoice= true
        isOpChoice= true
        isNewOpFunCoice=true
    }

    // Clean TextView
    fun btnClear(view: View){
        txtViewResult.text = ""
        txtViewEntry.text = ""
        arrElement.clear()
        //arrElement.removeAll(arrElement)
    }

    fun btOpDelete(view: View){
        if (arrElement.size==0){
            txtViewEntry.text=""
        }else{
            arrElement.removeAt(arrElement.size - 1)
            isNewChoice= false
            isOpChoice= false
            txtViewEntry.text= readFromArray(arrElement)
        }
    }
    fun calMul(arr: java.util.ArrayList<String>): java.util.ArrayList<String> {
        var elementToRemove = ArrayList<Int>()
        for (i in 0..arr.size-1){
            if(arr.get(i)=="*"){
                var result =df.format(multiplication(arr.get(i-1).toDouble(),arr.get(i+1).toDouble()))
                result= result.replace(",",".")
                arr.set(i+1,result)
                elementToRemove.add(i-1); elementToRemove.add(i)
            }
        }

        for (i in 0.. elementToRemove.size-1){
            arr.removeAt(elementToRemove.get(i)-i)
        }
        return arr
    }

    fun calDivision(arr: java.util.ArrayList<String>): java.util.ArrayList<String> {
        var result:Double
        var elementToRemove = java.util.ArrayList<Int>()
        for (i in 0..arr.size-1){
            if(arr.get(i)=="/"){
                result =  safeDivide(arr.get(i-1).toDouble(),arr.get(i+1).toDouble())
                if (java.lang.Double.compare(result, Double.POSITIVE_INFINITY) ==0 || java.lang.Double.compare(result, Double.NEGATIVE_INFINITY) ==0 ){
                    arr.set(i+1,result.toString())
                }else{
                    var resu= df.format(result)
                    resu= resu.replace(",",".")
                    arr.set(i+1,resu)
                }
                elementToRemove.add(i-1); elementToRemove.add(i)
            }
        }
        for (i in 0.. elementToRemove.size-1){
            arr.removeAt(elementToRemove.get(i)-i)
        }
        return arr
    }
    fun calMod(arr: java.util.ArrayList<String>): java.util.ArrayList<String> {
        var result:Double
        var elementToRemove = java.util.ArrayList<Int>()
        for (i in 0..arr.size-1){
            if(arr.get(i)=="%"){
                var result = df.format(modulo(arr.get(i-1).toDouble(),arr.get(i+1).toDouble()))
                result= result.replace(",",".")
                arr.set(i+1,result)
                elementToRemove.add(i-1); elementToRemove.add(i)
            }
        }
        for (i in 0.. elementToRemove.size-1){
            arr.removeAt(elementToRemove.get(i)-i)
        }
        return arr
    }
    fun callEnd(arr: java.util.ArrayList<String>): java.util.ArrayList<String> {
        var result:Double
        var elementToRemove = java.util.ArrayList<Int>()

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


    // calculate Operationen which the Program execute
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
            } else java.lang.Double.POSITIVE_INFINITY
        } else{

            dividend / divisor
        }
    }
    fun addition (a:Double, b: Double):Double{
        return a+b
    }
    fun subtraction(a:Double, b: Double):Double{
        return a-b
    }
    fun multiplication(a:Double, b: Double):Double{
        return a*b
    }
    fun division(a:Double, b: Double):Double{
        return safeDivide(a,b)
    }
    fun modulo(a:Double, b: Double):Double{
        return a%b
    }

    fun sinus(degree:Double):Double{
        var angle = df.format(sin(Math.toRadians(degree)))
        angle = angle.replace(",",".");
        return angle.toDouble()
    }
    fun inverseSinus(degree:Double):Double{
        var result = Math.toDegrees(asin(degree))
        if (java.lang.Double.compare(result, Double.NaN) ==0)
            return Double.NaN
        else{
            var resultSin= df.format(result)
            resultSin= resultSin.replace(",",".");
            return resultSin.toDouble()
        }
    }
    fun cosinus(degree:Double):Double{
        var angle = df.format(cos(Math.toRadians(degree)))
        angle = angle.replace(",",".");
        return angle.toDouble()
    }
    fun inverseCosinus(degree:Double):Double{
        var result = Math.toDegrees(acos(degree))
        if (java.lang.Double.compare(result, Double.NaN) ==0)
            return Double.NaN
        else{
            var resultSin = df.format(result)
            resultSin= resultSin.replace(",",".");
            return resultSin.toDouble()
        }
    }
    fun tangent(degree: Double):Double{
        var result= safeDivide(sinus(degree).toDouble(),cosinus(degree).toDouble())
        if(result== Double.NEGATIVE_INFINITY || result== Double.POSITIVE_INFINITY){
            return result
        }else{
            var angle= df.format(result)
            angle= angle.replace(",",".");
            return angle.toDouble()
        }
    }
    fun cotangent(degree: Double):Double{
        var result= safeDivide(cosinus(degree),sinus(degree))
        if(result== Double.NEGATIVE_INFINITY || result== Double.POSITIVE_INFINITY){
            return result
        }else{
            var angle= df.format(result)
            angle= angle.replace(",",".");
            return angle.toDouble()
        }
    }
    fun arcTang(degree:Double):Double{
        var tanResult = df.format(Math.toDegrees(Math.atan(degree)))
        tanResult = tanResult.replace(",",".")
        return tanResult.toDouble()
    }

    // Berechnet den natürlichen Logarithmus (Basis E ) des Wertes x .
    fun logn(degree: Double):Double{
        val re = ln(degree)
        if (java.lang.Double.compare(re, Double.NEGATIVE_INFINITY) == 0) {
            return Double.NEGATIVE_INFINITY
        } else {
            var angle = df.format(re)
            angle = angle.replace(",",".")
            return angle.toDouble()
        }
    }
    fun logarith(x: Double, base: Double): Double{
        var angle = df.format(log(x,base))
        angle= angle.replace(",",".")
        return angle.toDouble()
    }
    fun ex(degree: Double) :Double{
        var b = df.format(Math.exp(degree))
        b = b.replace(",",".")
        return b.toDouble()
    }
    fun logTen(x: Double):Double{
        var angle = df.format(log10(x))
        angle= angle.replace(",",".")
        return angle.toDouble()
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

    fun tenHx(x:Int):Double{
        if(x==0) {
            return 1.0;
        }else if(x>0) {
            return 10.0*tenHx(x-1);
        }
        else{
            var re= 1.0/(10*tenHx((-x)-1))
            var result = df.format(re)
            result= result.replace(",",".")
            return result.toDouble();
        }
    }
    fun squareRoot(x:Double):Double{
        val re = Math.sqrt(x)
        if (java.lang.Double.compare(re, Double.NaN) == 0) {
            return re
        } else {
            var angle = df.format(re)
            angle = angle.replace(",", ".")
            return angle.toDouble()
        }
    }

    fun factorial(n: Double): Double {
        try {
            return if (n >= 0) {
                if (n == 0.0 || n == 1.0) 1.0 else n * factorial(n - 1)
            } else
                throw java.lang.IllegalArgumentException("the entry must be >=0 ")
        } catch (e: Exception) {
            txtViewResult.text= ""+e
            return Double.NaN
        }
    }
    fun pi():Double{
        var p = df.format(Math.PI)
        p=p.replace(",",".")
        return p.toDouble()
    }

    fun inverseInput(x: Double):Double{
        return safeDivide(1.0,x)
    }
    fun radToDegree(x: Double){

    }
}
