package com.cugocumhurgunay.foodmenucreatorsql

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.cugocumhurgunay.foodmenucreatorsql.ui.theme.FoodMenuCreatorSQLTheme

class MainActivity : ComponentActivity() {
    private lateinit var kategoriRepository: KategoriRepository
    private lateinit var yemekRepository: YemekRepository
    private lateinit var yemekByKategori:MutableList<Yemek>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        kategoriRepository = KategoriRepository(this)
        yemekRepository = YemekRepository(this)


        setContent {
            FoodMenuCreatorSQLTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    kategoriRepository.KategorileriOlustur()
                    yemekByKategori = yemekRepository.getYemekler()
                    MainScreen()
                }
            }
        }
    }

    @Composable
    fun MainScreen()
    {
        val kategoriler = kategoriRepository.GetKategoriler()

        var expanded = remember { mutableStateOf(false) }
        var secilenKategori = remember { mutableStateOf(kategoriler[0] )  }

        Column(
            Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.gray)),
            Arrangement.Top, Alignment.Start
        )
        {
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.orange))
                .height(60.dp))
            {
                Row(
                    Modifier
                        .clickable {
                            expanded.value = !expanded.value
                        }
                        .align(Alignment.CenterStart))
                {
                    Text(text = secilenKategori.value.K_Aciklama,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(5.dp,0.dp,0.dp,0.dp))
                    Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null, tint = Color.White)
                }
                DropdownMenu(expanded = expanded.value,
                    onDismissRequest = {  expanded.value = false })
                {
                    kategoriler.forEach {

                        DropdownMenuItem(
                            text =
                            {
                                Text(text = it.K_Aciklama )
                            },
                            onClick = {
                                secilenKategori.value = it

                                expanded.value = false
                                if(secilenKategori.value.K_Aciklama == "Menü"){
                                    yemekByKategori = yemekRepository.getYemekler()
                                }else{
                                    yemekByKategori = yemekRepository.YemeklerByKategoriID(secilenKategori.value.K_ID)
                                }
                            })
                    }
                }
                YemekEkle()
            }
            YemekListesiGoster(lst = yemekByKategori)
        }

    }

    @Composable
    fun YemekGoster(y:Yemek) {
        val kategoriler = kategoriRepository.GetKategoriler()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.gray))
                .clip(RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            )
            {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = y.Adi,
                        style = MaterialTheme.typography.headlineSmall.copy(Color.Black),
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = kategoriler[y.Kategori_ID - 1].K_Aciklama,
                        style = MaterialTheme.typography.headlineMedium.copy(colorResource(id = R.color.orange)),
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Spacer(
                    Modifier
                        .padding(1.dp)
                        .fillMaxWidth()
                        .background(colorResource(id = R.color.gray))
                        .height(2.dp)
                )
                Text(text = y.Y_Aciklama,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(
                    Modifier
                        .padding(1.dp)
                        .fillMaxWidth()
                        .background(colorResource(id = R.color.gray))
                        .height(2.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Close Icon",
                        tint = Color.Gray,
                        modifier = Modifier.padding(8.dp,0.dp)
                            .clickable {
                                yemekRepository.silYemek(y.Y_ID)
                                yemekByKategori = yemekRepository.getYemekler()
                            }
                    )

                    Text(
                        text = "Fiyatı : ${y.Fiyati} TL",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Spacer(
                    Modifier
                        .padding(1.dp)
                        .fillMaxWidth()
                        .background(Color.White)
                        .height(5.dp)
                )
            }
        }
    }

    @Composable
    fun YemekListesiGoster(lst:List<Yemek>)
    {
        LazyColumn(
            modifier = Modifier
                .border(3.dp, colorResource(id = R.color.gray)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.Bottom,
            userScrollEnabled = true
        ) {
            items(lst) { item ->
                YemekGoster(y = item)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    @Composable
    fun YemekEkle() {
        var openDialog by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ) {
            SmallFloatingActionButton(
                onClick = {
                    openDialog = true
                },
                modifier = Modifier.padding(8.dp),
                containerColor = Color.White
            ) {
                Icon(
                    Icons.Filled.Add,
                    "Fab",
                    tint = colorResource(id = R.color.orange),
                    modifier = Modifier.size(34.dp)
                )
            }
        }
        if (openDialog) {
            YemekEkleDialog(openDialog) { openDialog = false }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun YemekEkleDialog(openDialog: Boolean, onClose: () -> Unit) {
        val context = LocalContext.current
        val customTextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.orange),
            unfocusedBorderColor = Color.Black,
            focusedLabelColor = colorResource(id = R.color.orange),
            cursorColor = Color.Black
        )

        if (openDialog) {
            Dialog(onDismissRequest = { onClose() }) {
                Surface {
                    val kategoriler = kategoriRepository.GetKategoriler()
                    kategoriler.removeAt(0)
                    var expanded = remember { mutableStateOf(false) }
                    var secilenKategori = remember { mutableStateOf(kategoriler[0]) }

                    val tfYemekAdi = remember { mutableStateOf("") }
                    val tfYemekAciklama = remember { mutableStateOf("") }
                    val tfYemekFiyati = remember { mutableStateOf("") }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        Box(
                            modifier = Modifier
                                .background(color = colorResource(id = R.color.orange))
                                .fillMaxWidth()
                                .height(50.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Yemek Ekle",
                                    fontSize = 20.sp,
                                    color = Color.White,
                                    style = MaterialTheme.typography.titleMedium,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.weight(1f)
                                ) // Add some space between text and icon
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Close Icon",
                                    tint = Color.White,
                                    modifier = Modifier.clickable {onClose()}
                                )
                            }
                        }


                        OutlinedTextField(
                            value = tfYemekAdi.value,
                            onValueChange = {tfYemekAdi.value = it},
                            label = { Text("Yemek Adı") },
                            textStyle = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp),
                            colors = customTextFieldColors
                        )

                        OutlinedTextField(
                            value = tfYemekFiyati.value,
                            onValueChange = {tfYemekFiyati.value = it},
                            label = { Text("Yemek Fiyatı") },
                            textStyle = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            ),
                            colors = customTextFieldColors
                        )

                        OutlinedTextField(
                            value = tfYemekAciklama.value,
                            onValueChange = {tfYemekAciklama.value = it},
                            label = { Text("Açıklama") },
                            textStyle = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp),
                            colors = customTextFieldColors

                        )

                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .background(colorResource(id = R.color.white))
                            .height(50.dp)
                            .padding(start = 16.dp, end = 16.dp)
                            .border(1.dp, color = Color.Black, shape = RoundedCornerShape(5.dp))
                        )

                        {
                            Row(
                                Modifier
                                    .clickable {
                                        expanded.value = !expanded.value
                                    }
                                    .align(Alignment.Center))
                            {
                                    Text(text = secilenKategori.value.K_Aciklama,
                                        fontSize = 16.sp,
                                        color = Color.Black,
                                        style = MaterialTheme.typography.titleMedium,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(5.dp,0.dp,0.dp,0.dp))

                                Icon(imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.align(Alignment.CenterVertically))
                            }
                            DropdownMenu(expanded = expanded.value,
                                onDismissRequest = {  expanded.value = false })
                            {
                                kategoriler.forEach {

                                    DropdownMenuItem(
                                        text =
                                        {
                                            Text(text = it.K_Aciklama )
                                        },
                                        onClick = {
                                            secilenKategori.value = it

                                            expanded.value = false

                                            yemekByKategori = yemekRepository.YemeklerByKategoriID(secilenKategori.value.K_ID)
                                        })
                                }
                            }
                        }


                        Button(
                            colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.orange),
                            contentColor = Color.White,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp, 0.dp, 8.dp, 8.dp)
                                .background(
                                    shape = RoundedCornerShape(10.dp),
                                    color = colorResource(id = R.color.orange)
                                ),
                            onClick = {
                            val yemekAdi = tfYemekAdi.value
                            val yemekAciklama = tfYemekAciklama.value
                            if(yemekAdi.isNotEmpty()&&yemekAciklama.isNotEmpty()&&tfYemekFiyati.value.isNotEmpty()){
                                val yemekFiyat = tfYemekFiyati.value.trim().replace(",", ".").toDouble()
                                yemekRepository.YemekEkle(
                                    Yemek(-1,
                                        secilenKategori.value.K_ID,
                                        yemekAdi,
                                        yemekAciklama,
                                        yemekFiyat)
                                )
                                onClose()
                                yemekByKategori = yemekRepository.getYemekler()
                            }else{
                                Toast.makeText(context, "Lütfen Gerekli Alanları Doldurunuz", Toast.LENGTH_SHORT).show()
                            }
                            })
                        {
                            Text(text = "KAYDET")
                        }
                    }
                }
            }
        }
    }
}
