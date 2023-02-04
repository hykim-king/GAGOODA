import datetime

from bs4 import BeautifulSoup
import requests
import time
from datetime import date
import random
import csv


category_num = {
    "침실": 1,
        "1침대": 11,
            "11모션베드": 111,
            "11일반침대": 112,
            "11패밀리침대": 113,
            "11매트리스": 114,
            "11액세서리": 115,
        "1매트리스": 12,
            "12매트리스": 121,
            "12커버 / 패드": 122,
        "1옷장": 13,
            "13옷장/붙박이장": 131,
            "13드레스룸": 132,
            "13수납가구/ACC": 133,
        "1수납장": 14,
            "14협탁": 141,
            "14서랍장": 142,
            "14파티션수납장": 143,
        "1화장대": 15,
            "15화장대": 151,
            "15스툴": 152,
            "15거울/액세서리": 153,
    "거실": 2,
        "2테이블": 21,
            "21소파 테이블": 211,
            "21패밀리 테이블": 212,
            "21멀티테이블": 213,
            "21액세서리": 214,
        "2거실장": 22,
            "22거실장": 221,
        "2소파": 23,
            "23일자형": 231,
            "23카우치/코너": 232,
            "231인/소파베드": 233,
            "23리클라이너": 234,
            "23액세서리": 235,
        "2수납장": 24,
            "24책장": 241,
            "24장식장": 242,
            "24서랍장": 243,
            "24액세서리": 244,
        "2액세서리": 25,
    "주방": 3,
        "3식탁": 31,
            "31식탁": 311,
        "3수납장": 32,
            "32수납장": 321,
            "32액세서리": 322,
        "3의자": 33,
    "키즈룸": 4,
        "4침대": 41,
            "41침대": 411,
            "41매트리스": 412,
            "41액세서리": 413,
        "4책상": 42,
            "42책상": 421,
            "42액세서리": 422,
        "4수납장": 43,
            "43서랍/수납장": 431,
            "43책장": 432,
            "43옷장/행거": 433,
            "43놀이장": 434,
        "4소파/의자": 44,
            "44소파": 441,
            "44의자": 442,
        "4액세서리": 45,
    "학생방": 5,
        "5책상": 51,
            "51일반형": 511,
            "51기능형": 512,
            "51액세서리": 513,
        "5책장": 52,
            "52책장": 521,
            "52액세서리": 522,
        "5수납장": 53,
            "53서랍장": 531,
            "53옷장": 532,
            "53수납가구 / ACC": 533,
        "5의자": 54,
            "54의자": 541,
            "54액세서리": 542,
        "5침대": 55,
            "55일반침대": 551,
            "55수납침대": 552,
            "55모션침대": 553,
            "55매트리스": 554,
            "55액세서리": 555,
    "서재": 6,
        "6책상": 61,
            "61일반형": 611,
            "61모션형": 612,
            "61액세서리": 613,
        "6책장": 62,
            "62책장": 621,
            "62액세서리": 622,
        "6서랍장": 63,
            "63책상서랍": 631,
        "6의자": 64,
            "64태스크의자": 641,
            "64보조의자": 642,
            "64프리미엄의자": 643,
        "6수납장": 65,
            "65장식장": 651
}


def csv_save(csv_path, row):
    # print(row)
    # return
    with open(csv_path, 'a', newline='', encoding='utf-8') as f:
        writer = csv.writer(f)
        writer.writerow(row)


def img_save(img_path, content):
    # return
    with open(img_path, 'wb') as f:
        f.write(content)


class Crawler:
    def __init__(self):
        # 크롤링을 시작 할 페이지
<<<<<<< HEAD
        self.start_url = "https://www.iloom.com/product/item.do?categoryNo=26"
        self.visited_list = set()
        self.to_visit = set()
        # 상품과 연결될 카테고리
        self.category = ['학생방', '의자']
        # csv 파일이 저장될 경로
        self.csv_path = "C:/Users/Hanbi/Gagooda/yhb/crawling_data/"
        # 이미지 파일이 저장될 경로
        self.img_path = 'C:/Users/Hanbi/Gagooda/yhb/crawling_img/'
=======
        self.start_url = "https://www.iloom.com/product/item.do?categoryNo=17"
        self.visited_list = set()
        self.to_visit = set()
        # 상품과 연결될 카테고리
        self.category = ['주방', '식탁']
        # csv 파일이 저장될 경로
        self.csv_path = "/Users/yanghanna/Documents/BIG_AI0102/csv_path/"
        # 이미지 파일이 저장될 경로
        self.img_path = '/Users/yanghanna/Documents/BIG_AI0102/img_path/'
>>>>>>> 14b72b16e296291c42ec6ee0c19a73762751dd15

    def parse_html(self, url):
        soup = None
        try:
            response = requests.get(url)

            if response.status_code == 200:
                html = response.text
                soup = BeautifulSoup(html, "html.parser")
            else:
                print('response.status_code:{}'.format(response.status_code))
                print("url을 확인 하세요")
        except Exception as e:
            print("*"*40)
            print("parse_html Exception: {}".format(e))
            print("*"*40)

        return soup

    def find_product_list(self, soup):
        if soup:
            pro_list = soup.select("div.pro_list>div>ul.proUl>li")
            product_cd_list = []
            for product in pro_list:
                product_cd = product.get("data-product-cd")
                product_cd_list.append(product_cd)
            return product_cd_list

    def product_info_parse(self, product_cd):
        url = "https://www.iloom.com/product/detail.do?productCd=" + product_cd
        product_detail = self.parse_html(url)

        if not product_detail:
            return

        # 상품 상세 정보 박스 --------------------------------
        box_product_info = product_detail.select_one("div.box_productInfo")
        # 상품명
        product_name = box_product_info.select_one("div.productNm").text.strip()

        # 상품 가격
        product_price_box = box_product_info.select_one("div.price")
        product_price_span = product_price_box.select("span")
        if product_price_span:
            # 할인중
            print("할인중인 상품")
            product_price_txt = product_price_span[0].text.strip()[:-1].strip()
        else:
            # 보통 가격
            product_price_txt = product_price_box.text.strip()[:-1]
        # 판매가
        product_sp = int(product_price_txt.replace(",", ""))
        # 원가
        product_op = int((7/10*product_sp)/1000)*1000

        # product 테이블 데이터 추가 ---------------
        product_csv_path = self.csv_path+"product.csv"
        # [상품 코드, 상품명, 판매가, 원가, 마진율, 과세율, 진열 상태, 판매 상태, 상품 이미지 코드]
        row = [product_cd, product_name, product_sp, product_op, 0.3, 0.1, 1, 1, product_cd]
        csv_save(product_csv_path, row)

        # 상품 카테고리 연결 ----------------
        conn_csv_path = self.csv_path+"connection.csv"
        cat = ""
        for category in self.category:
            cat_num = category_num[cat+category]
            row = [product_cd, cat_num]
            cat = str(cat_num)
            csv_save(conn_csv_path, row)

            print(category+"  "+str(cat_num))

        # 상품 색깔 옵션 ---------------------
        select_color = box_product_info.select("select.select_color > option")
        for product in select_color:
            color = product.get("data-product-col")
            if color:
                opt_csv_path = self.csv_path+"option_product.csv"
                # [상품 아이디, 옵션 추가 플래그, 옵션 상품명, 상품 가격, 수향]
                row = [product_cd, 0, product_cd + "_" + str(color), product_sp, 100]
                csv_save(opt_csv_path, row)

        # 상품 상세 정보 박스 END --------------------------------

        # 상품 이미지 박스 -----------------------------
        box_product_gal = product_detail.select_one("ul.box_productGalery_S")
        img_src_list = box_product_gal.select("li>img")
        i = 0
        for img_src in img_src_list:
            img_url = img_src.get("src")
            if img_url:
                file_name = product_cd+"_"+str(i)+".png"

                # 이미지 저장
                img_path = self.img_path + file_name
                content = requests.get(img_url).content
                img_save(img_path, content)

                # 이미지와 상품 연결
                img_csv_path = self.csv_path + "image.csv"
                # [상품 코드, 이미지 종류, 이미지 파일명]
                row = [product_cd, 1, file_name]
                csv_save(img_csv_path, row)
                i += 1

    def investigate_page(self, url, page_no):
        # 페이지 정보 가져오기
        soup = self.parse_html(url+str(page_no))
        if not soup:
            return

        # 현재 페이지 속에 있는 상품 코드들
        product_cd_list = self.find_product_list(soup)
        if not product_cd_list:
            return
        # print(product_cd_list)

        # 코드들로 상품 상세 페이지로 가서 상세 정보 가져오기
        for product_cd in product_cd_list:
            print("-"*50)
            print(product_cd)
            self.product_info_parse(product_cd)
            print("-"*50)

        # 현재 페이지에서 방문해야될 url 찾기
        self.investigate_page(url, page_no+1)

    def run(self):
        # 전체를 먼저 크롤링
        self.investigate_page(self.start_url+"&pageNo=", 1)

        soup = self.parse_html(self.start_url)
        a_tag_list = soup.select("div.toggleDiv > a")
        for a_tag in a_tag_list:
            if len(self.category) > 2:
                self.category.pop()
            url = "https://www.iloom.com"+a_tag.get("href")+"&pageNo="
            category_name = a_tag.select_one("span").text
            self.category.append(category_name)
            print(self.category)
            self.investigate_page(url, 1)

    def test(self):
        # url = "https://www.iloom.com/product/detail.do?productCd=HCS824V"
        self.product_info_parse("HB722501")
        self.product_info_parse("HCS824V")


def main():
    if __name__ == "__main__":
        crawler = Crawler()
        crawler.run()


main()
