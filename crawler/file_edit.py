"""
파일명 : file_edit.py
설 명 : 
생성일 : 2023-02-02
생성자 : hamin
since 2023.01.09 Copyright (C) by Hamin All right reserved.
"""
import csv

def compact_data():
    dir = "C:/Users/hamin/Desktop/data/"
    # name_list = ['chm', 'chy', 'ckl', 'kdh', 'kdy', 'khs', 'osh1', 'osh2', 'osh3', 'yhb', 'yhn']
    name_list = ['chm', 'ckl']
    file_list = ['connection.csv', 'image.csv', 'option_product.csv', 'product.csv']

    new_dir = 'C:/Users/hamin/Desktop/data/new/csv/'

    for name in name_list:
        name_dir = dir + name + "/csv/"  # C:/Users/hamin/Desktop/data/[name/]
        for file in file_list:
            csv_dir = name_dir + file
            try:
                row_list = []
                with open(csv_dir, 'r', newline='', encoding="utf-8") as f:
                    reader = csv.reader(f)
                    for row in reader:
                        print(row)
                        row_list.append(row)
                with open(new_dir+file, 'a', newline='', encoding="utf-8") as f:
                    writer = csv.writer(f)
                    writer.writerows(row_list)
            except Exception as e:
                print("*"*50)
                print("{}, {} 에서 오류가 났습니다.".format(name, file))
                print(e)
                print("*"*50)
                return



def main():
    compact_data()


main()
