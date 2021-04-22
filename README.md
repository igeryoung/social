# oop 自選題 report
### 組員 B07902060 趙雋同, B07902058 楊盛評, B07902109張翔文
![](https://i.imgur.com/uY1KZ8n.png)
## Development
- 使用軟體: 
Android Studio 3.6.3
FireBase
- 合作方式
使用同一個Github repo來同步撰寫同一份code
網址: https://github.com/igeryoung/social
由於我們對Github還不夠熟悉，有時會發生不同版本間Merge的問題，發生好不容易寫完code想要push但卻發生conflict的狀況，熟悉Git是我們未來可以改善的地方。

## Package規劃
### login
- 簡介：
使用者輸入帳密以後和DataBase比對資料，成功後進入下一個頁面Swipe Activity.
- Class 介紹：
    - MainActivity: 打開程式後進入的第一個介面，從帳號密碼的layout讀入資料以後丟入Database驗證，若驗證無誤以後就進Swipe Activity．
- 頁面內容：
    - 帳號與密碼輸入欄
        - 密碼加入防盜處理
    - 登入按鍵：
        - 若是第一次登入會跳到個人資料頁面
        - 若已填完個人資料則會到主畫面
    - 註冊頁面轉跳鈕
- Exception Handle：
    - 1.若有任一欄留空則會跳出警告
    - 2.若密碼與不正確會跳出警告
### register
- 簡介：
使用者輸入帳密以後和DataBase比對資料，成功後將帳密加入DataBase.
- Class 介紹：
    - RegisterActivity: 從帳號密碼的layout讀入資料以後丟入Database驗證，若驗證無誤以後就退回登入介面．
- 頁面內容：
    - 帳號、密碼、確認密碼輸入欄
    - 檢查帳號按鈕：可檢查Database內有無相同帳號被註冊過
    - 註冊按鈕：若成功則會回到登入頁
- Exception Handle：
    - 1.若有任一欄留空則會跳出警告
    - 2.若密碼與確認密碼不合會跳出警告
### personal_information
- 簡介:
從DataBase接收用戶資料顯示給用戶端，或是從用戶端讀取新的資料來加入DataBase
- Class 介紹:
    - PersonalInformationActivity: 顯示DataBase回傳的用戶資料，並更新用戶端輸入的資料
    - PersonalInformationException: 處理用戶填錯資料或漏填資料時的Exception
    - Photo: 儲存PersonalInformation中大頭貼的相關的資料
- 頁面內容:
    - 外框為圓形的大頭貼，點擊後可以從圖片庫中選取圖片上傳
    - 個人資料文字欄位包含:
        - 姓名, 性別, 年紀, 大學, 城市, 個性, 興趣, 關於我
- Exception Handle:
    - 若有任一欄留空則會跳出警告
- 遇到的困難
    - 從用戶端的圖片庫讀入圖片時，若是使用他的uri直接呈現於App上會導致系統崩潰，正確的做法是先將圖片壓縮再使用，或是使用Picasso套件也可以處理好圖片。
    - 從Database上下載圖片url轉bitmap時MediaStore套件的getbitmap也會無法下載，正確的作法仍是使用Picasso來處理下載的問題。

### image - Main Page
由2個部分組成:
### 1. ui/home
- 簡介:
陌生人照片顯示，能夠左滑右滑來表示喜歡或不喜歡，或是使用按鈕來自動右滑左滑
參考github上的開源碼:https://github.com/yuyakaido/CardStackView

- Class介紹:
    - SwipeActivity:滑動頁面的主控台，負責在圖片被滑動以後將使用者資料傳入DataBase，和傳資料給CardStackView
    - CardStackAdapter: 從Swipe Activity接收用戶資料以後將大頭貼．名字等資料並顯示在PersonalInformation頁面上．
    - CircleTransform: 使用Picasso時把方形的圖片轉為圓形的頭貼．
- Builder design
    - SwipeActivity在建立CardStackView時，只負責設定參數，其他的功能交給CardStackAdapter去實行。
- 遇到的困難:
    - 由於主要參考網路上的Open source code，故相關資料較少，在查找資料時大多只能直接去看code研究．
- 主要功能:
    - 左右滑動圖片:右滑代表喜歡，左滑則是不喜歡，若有2個人互相起歡，則會加入對方的好友名單內
### 2. menu
- 簡介:
顯示出目前的使用者照片和姓名與提供其他的服務內容
利用android studio提供的 navigation drawer layout 進行修改
- 清單的子物件
    - 個人資料:personal_information
    - 好友名單:friend
    - 登出:login
- 遇到的困難:
    - studio提供的結構太複雜，還牽涉到fragment和activity的交互作用，在實作時難以理解他提供的code所代表的意義

### database

- Firestore
    - 簡介: 
    Cloud Firestore 是 Google 所提供的新一代即時資料庫，延續 RealTime Database 的高效率以及低延遲性，可以即時監聽資料庫的變化，並同步有使用到資料庫的各個應用。
    - 實作:
    分別建立三個collection存放資料：分別是account, personalInformation和relation
        - accountDB : 
        存放帳戶相關資料，提供確認帳戶是否存在、密碼是否正確、更改密碼和註冊等api
        - personalInformationDB : 
        存放各使用者的個人資料，並且提供新增資料、修改資料、索取特定人資料等api
        - relationDB : 
        存放各使用者瀏覽過的用戶名單，並且記錄喜歡以及不喜歡的人和將互相喜歡的人及時配對成好友，提供新增好友、新增喜歡等api
    - 遇到的困難:
    由於firestore的函式都屬於asynchronous，所以我們採取了設置addOnSuccessListener以及addOnCompleteListener，令api會在資料傳回時才將資料載入到下一個頁面。以及因為firestore屬於NoSQL資料庫，當初在研究時花費了不少時間了解各函式的意義。

- Firebase storage
    - 簡介:
    是專為需要存儲和呈現用戶生成的內容（例如照片或視頻）的應用開發者構建。不管網絡質量如何，Firebase Storage 都可以為 Firebase 應用提供安全的文件上傳與下載。 開發者可以使用它存儲圖片、音頻、視頻或其他用戶生成的內容。
    - 實作:
    將每個用戶的大頭貼以及其照片上傳到storage中，並且將上傳成功的網址透過personalInformationDB更新其個人資料，此後當使用者需要照片時只需透過網址下載

### friend
- 簡介:
利用RecyclerView來展示目前用戶的朋友列表
- Class介紹:
    - FriendActivity:建立朋友清單、對清單的維護與處理點擊事件
    - FriendInfoActivity:顯示被點選的用戶資料，與Personal_Infomation類似
- 主要功能:
    - 用RecyclerView來顯示清單以支援清單的滑動
    - 透過viewholder來進行資料的更新與點擊事件

### model
- 簡介:
model儲存我們為了資料管理創建的Object Class
- Class介紹
    - Account:內有帳號、密碼、以及是否填寫Personal_Infomation，主要用於登入
    - PersonalInformation:儲存使用者的詳細資料，包括帳號、名稱、照片uri、關於我、大學、城市、年齡、性別、興趣、個性

## Design Pattern


## 發展性
和現今網路上熱門的交友App來比，我們還缺少一些功能，比如：
1. 聊天室功能： 為了讓用戶能夠更深入的交流，我們還可以加入聊天室的功能讓用戶可以在上面打字聊天，還可以加上讓用戶傳送圖片的功能．
2. 資安問題： 帳號密碼、大頭照等用戶資料在傳輸時若被攻擊者截獲，恐怕會造成個資洩漏的影響，故在傳輸資料時先加密以後再傳送會是一個比較好的辦法．
3. 圖片庫功能：由於資料庫空間大小的限制，目前每個用戶我們只儲存一張圖片，若是獲得金錢上的資助，我們還可以擴充資料庫的存量和傳輸量的上限，讓用戶可以儲存更多圖片．
4. 標籤擇友功能：根據每一個用戶填寫的個人資料，將每個用戶貼上一些標籤，之後再根據標籤來把有相近標籤的用戶匹配在一起，或是讓用戶可以手動過濾一些有特定標籤的用戶．

## 附錄:
Android Studio 開發頁面:  
![](https://i.imgur.com/IjP4Kfi.png)  
FireBase 開發頁面:  
![](https://i.imgur.com/9ad7Jmm.png)  
Github 開發介面:  
![](https://i.imgur.com/6cNnkBm.png)  

Login 頁面:  
![](https://i.imgur.com/9loBm1L.png)  
Register 頁面:  
![](https://i.imgur.com/e7dF1VZ.png)  
Personal Information介面:  
![](https://i.imgur.com/BlayJgB.png)  
滑動頁面:  
![](https://i.imgur.com/Kew0ldc.png)  
ToolBar:  
![](https://i.imgur.com/CAC5OTj.png)  
