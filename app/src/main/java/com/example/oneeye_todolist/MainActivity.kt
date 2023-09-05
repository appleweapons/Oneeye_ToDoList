package com.example.oneeye_todolist

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.oneeye_todolist.ui.theme.Oneeye_ToDoListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Oneeye_ToDoListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 구성할 화면 입력
                    // 모든 기기에 호환을 위해 아래 코드 적용
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column(
                            // 기존의 Modifier.fillMaxWidth()와 Modifier.fillMaxHeight()를 사용
                            // 화면 크기에 따라 유연성 있게 조정
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // 할 일 입력 칸 갯수 정의 위해 변수 생성
                            val todos = List(9) { mutableStateOf("") }
                            val isCompletedList = List(9) { mutableStateOf(false) }
                            // 윗칸 생성 + 앱 제목(To Do List) 배치
                            Spacer(modifier = Modifier.height(10.dp))
                            Box {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "")
                                    Text(text = "To Do List")
                                    Text(text = "")
                                }
                            }
                            Spacer(modifier = Modifier.height(7.dp))
                            Divider(modifier = Modifier.height(3.dp))
                            Spacer(modifier = Modifier.height(7.dp))
                            // 반복문 통해 할 일 입력칸 생성 (TextField, Button 은 아래 @Composable 에 있음)
                            for (i in 0 until 9) {
                                TodoInputRow(
                                    todo = todos[i],
                                    index = i + 1,
                                    isCompleted = isCompletedList[i]
                                )
                            }
                            // 아랫칸 생성
                            Spacer(modifier = Modifier.height(7.dp))
                            Divider(modifier = Modifier.height(3.dp))
                            Spacer(modifier = Modifier.height(7.dp))
                            Box {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "")
                                    // 초기화 버튼 배치 및 처리 부분
                                    Button(
                                        onClick = {
                                            todos.forEachIndexed { index, todo ->
                                                todo.value = ""
                                                isCompletedList[index].value = false
                                            }
                                            // 초기화 버튼 누를 시 "초기화되었습니다." 말풍선 띄우기
                                            Toast.makeText(
                                                this@MainActivity,
                                                "초기화되었습니다.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        },
                                        modifier = Modifier.width(100.dp)
                                    ) {
                                        Text(text = "초기화")
                                    }
                                    Text(text = "")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// 할 일 적는 칸 및 버튼 배치 관련
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoInputRow(todo: MutableState<String>, index: Int, isCompleted: MutableState<Boolean>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // 할 일 적는 칸 구현
        TextField(
            value = todo.value,
            onValueChange = { newValue ->
                // 완료 상태가 아닌 경우에 한해 텍스트 수정 가능 하도록 조건 추가
                if (!isCompleted.value) {
                    todo.value = newValue
                }
            },
            label = { Text(text = "할 일 입력 $index") },
            modifier = Modifier
                .height(57.dp)
                .width(285.dp)
                .weight(1f)
        )
        Spacer(modifier = Modifier.width(15.dp))
        // 버튼 기능 구현
        Button(
            onClick = {
                if (!isCompleted.value) {
                    todo.value += " (완료)"
                    isCompleted.value = true
                }
            },
            modifier = Modifier.width(80.dp),
            // 완료 상태인 경우 버튼 활성화 안됨
            enabled = !isCompleted.value
        ) {
            Text(text = "완료")
        }
    }
}