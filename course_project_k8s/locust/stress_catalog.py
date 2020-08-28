import time, random
import os
import faker

fake = faker.Faker()
# import faker
# from requests_toolbelt.utils import dump

from locust import HttpUser, task, between

class QuickstartUser(HttpUser):
    wait_time = between(1, 2)

#    @task(3)
#    def history(self):
#      for counter in range(100):
#        self.client.get("/history/all")
#        self.client.get("/history/?CUSTOMER=Tristian.Leannon@hotmail.com") 
#        time.sleep(1)

 
    @task(3)
    def products(self):
      self.client.get("/catalog/brand/")
      self.client.get("/catalog/category/")
      for category_id in range(5):
        for brand_id in range(5):
          self.client.post("/catalog/search/", json={ "brandId" : f"{brand_id}", "categoryId": f"{category_id}", "name" : "a" })
            # self.client.post("/catalog/search/", json={ "brandId" : f"{brand_id}", "categoryId": f"{category_id}", "name" : fake.word() })
            # time.sleep(1)
          


    def on_start(self):
      first_name = fake.first_name()
      last_name = fake.last_name() 
      idx = random.randint(1, 10000)
      login = f"{first_name}_{last_name}_{idx}"
      self.client.post("/register", json={"login":f"{login}", "password":"coolpassword", "email": f"{login}@nogmail.com", "firstName" : f"{first_name}", "lastName" : f"{last_name}", "phone" : fake.phone_number() })
      self.client.post("/login", json={"login":f"{login}", "password":"coolpassword"})

