"""
파일명 : dup_check.py
설 명 :
생성일 : 2023-02-01
생성자 : hamin
since 2023.01.09 Copyright (C) by Hamin All right reserved.
"""

import csv


def dup_check(csv_file_path):
    list = []
    with open(csv_file_path, 'r', newline='', encoding='utf-8') as f:
        reader = csv.reader(f)
        for row in reader:
            if row not in list:
                list.append(row)

        print("-"*50)
        print("{}".format(csv_file_path))
        print("original len: {}".format(reader.line_num))
        print("changed len: {}".format(len(list)))
        print("-"*50)

    with open(csv_file_path, 'w', newline='', encoding='utf-8') as f:
        writer = csv.writer(f)
        writer.writerows(list)


def main():

    file_dir = "C:/Users/oyunm/Desktop/crawling_chy/"
    file_list = ["connection.csv", "image.csv", "option_product.csv", "product.csv"]
    for file_name in file_list:
        file_path = file_dir+file_name
        dup_check(file_path)


main()

'''
--------------------------------------------------
C:/Users/hamin/Desktop/data/new/csv/connection.csv
original len: 4170
changed len: 3861
--------------------------------------------------
--------------------------------------------------
C:/Users/hamin/Desktop/data/new/csv/image.csv
original len: 4476
changed len: 3542
--------------------------------------------------
--------------------------------------------------
C:/Users/hamin/Desktop/data/new/csv/option_product.csv
original len: 3078
changed len: 2450
--------------------------------------------------
--------------------------------------------------
C:/Users/hamin/Desktop/data/new/csv/product.csv
original len: 1583
changed len: 1071
--------------------------------------------------
'''
