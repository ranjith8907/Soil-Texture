import cv2
import matplotlib.pyplot as plt
import keras.utils as image
import numpy as np
from tensorflow.keras.models import Sequential

def predict_soil(path):
    im=cv2.imread(path)
    if im is not None:
        im_resized=cv2.resize(im,(150,150),interpolation=cv2.INTER_LINEAR)

        #plt.imshow(cv2.cvtColor(im_resized,cv2.COLOR_BGR2RGB))
        #plt.show()

        img_pred=image.load_img(path,target_size=(150,150))
        img_pred=image.img_to_array(img_pred)
        img=np.expand_dims(img_pred,axis=0)
        model = Sequential()
        result=model.predict_classes(img)
        prob=model.predict_proba(img)
        print('Predicted class:',result)
        print('Probability:{}'.format(prob[0]))
        if result[0]==0:
            prediction="Alluvial_Soil"
        elif result[0]==1:
            prediction="Black_Soil"
        elif result[0]==2:
            prediction="Clay_Soil"
        else:
            prediction="Red_Soil"
        
        print('predicted Class:', prediction)
    else:
        print("hi")

predict_soil("E:/soil/train/Sand/0.jpg")    
    
