import os

def renamechange(outputpath):
      path=os.chdir(outputpath)
      i=0
      for file in os.listdir(path):
          newfile_name="{}.jpg".format(i)
          os.rename(file,newfile_name)
          i=i+1

if __name__ == "__main__":
    pass          
