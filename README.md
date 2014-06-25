TOC_HW4
=======

Theory of Computation HW4


class 'Road'用來紀錄每條路的特性，包含路名、最大最小交易價、交易出現的月份和其次數，  
裡面除了各個variable的get、set function外，還有'Road()'用來初始化，  
一開始的最大交易價設為0、最小交易價射程30000000，'setRoad'用在第一次記錄，多了路名這個參數，  
'addMonth'就用來記錄有出現過的路段，除了紀錄路的交易月份外，還要記錄交易金額  
，用'changePrice'來跟最大最小值做比較和變動。
  
  
'main' 裡，一開始從program第1個參數讀取url，下載資料到data.json，之後再讀黨放入一json array，比較路名使否紀錄過，  
這裡路名包括XX路、XX巷和XX街，整個json array 記錄完後，再找出交易月份最多月份的路段，印出路名和最大最小交易量。  
