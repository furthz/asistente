# -*- coding: utf-8 -*-
"""
Created on Tue Mar 13 21:56:13 2018

@author: Furth
"""

import numpy as np
import cv2
import sys


def estan_cercanos(a1, a2, error):
    cases = np.unwrap([a2-error, a1, a2 + error])
    return cases[0] <= cases[1] <= cases[2]

def enderezar(entrada, salida):
    # Leer la imagen
    imagen = cv2.imread(entrada)

    # Convertirla a gris y detectar bordes
    gray = cv2.cvtColor(imagen, cv2.COLOR_BGR2GRAY)
    binaria = cv2.Canny(gray,50,150,apertureSize = 3)
    
    #cv2.imshow('Grayscale', imagen)#, binaria)

    # Usar la transformada de Hough para encontrar líneas
    # en la imagen binarizada, con una resolución de medio
    # grado (pi/720) y quedándose sólo con las líneas que
    # alcancen puntuación de 1000 o más (que serán las
    # más largas)
    lineas = cv2.HoughLines(binaria, 1, np.pi/720, 400)

    # Recopilemos qué ángulos ha encontrado la transformada
    # de hough para cada una de las líneas halladas
    angulos = []

    try:
        for linea in lineas:
            rho, theta = linea[0]
            if rho<0:
                theta = -theta
    
            # Quedarse solo con las rayas próximas a la horizontal
            # (con un error de +-10 grados)
            if not estan_cercanos(theta, np.pi/2, np.deg2rad(10)):
               continue;
               
            angulos.append(theta)
            
        from collections import Counter
        veces = Counter(angulos)
    
        # Quedémonos con los tres casos más frecuentes
        frecuentes = veces.most_common(3)
    
        # Y calculemos el promedio de esos tres casos
        suma = sum(angulo*repeticion for angulo,repeticion in frecuentes)
        repeticiones = sum(repeticion for angulo, repeticion in frecuentes)
        angulo = suma/repeticiones
    
        angulo = np.rad2deg(angulo - np.pi/2)
        print("[INFO] angulo: {:.5f}".format(angulo))
    
        W = 1200.
        height, width, depth = imagen.shape
        imgScale = W/width
        newX,newY = imagen.shape[1]*imgScale, imagen.shape[0]*imgScale
        
        # Ahora enderecemos la imagen, girando el ángulo detectado
        (h, w) = imagen.shape[:2]
        centro = (w // 2, h // 2)
        M = cv2.getRotationMatrix2D(centro, angulo, 1.0)
    
        girada = cv2.warpAffine(imagen, M, (w, h),
                    flags=cv2.INTER_CUBIC, borderMode=cv2.BORDER_REPLICATE)
        
        girada = cv2.resize(girada,(int(newX),int(newY)), interpolation = cv2.INTER_AREA)
        
            # Y volcamos a disco el resultado
        cv2.imwrite(salida, girada)
    except:        
        W = 1200.
        height, width, depth = imagen.shape
        imgScale = W/width
        newX,newY = imagen.shape[1]*imgScale, imagen.shape[0]*imgScale
        print("Solo resize")
        girada = cv2.resize(imagen,(int(newX),int(newY)), interpolation = cv2.INTER_AREA)
        cv2.imwrite(salida, girada)

    # Ahora contemos cuántas veces aparece cada ángulo
    
    
    
#enderezar("convert-contr61548529.1.png","convert-contr61548529.1.png")
enderezar(sys.argv[1],sys.argv[2])