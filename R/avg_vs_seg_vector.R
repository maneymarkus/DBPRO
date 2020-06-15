# Run with:
# ---------
#  > source('~/.../avg_vs_seg_vector.R') 
#  
#  > avg_vs_seg('~/.../a5.csv','~/.../alternative.csv','~/.../vektor.txt' '1711296', 'mixing')


# date_file = first csv-file
# ref_file = second csv-file
# vector_file = plain txt with num on each line, total num = 168 -- vector of DTW or Eukl. Distance
# format:
#
#     12.79
#     23.45
#     59.23
#     . . .
avg_vs_seg <- function(data_file, ref_file, vector_file, seg,  measure) {

library(DAAG) # for pause())
library(scales)
library(ggplot2)
#library(dtw)
  
    s <- read.csv(data_file) # first csv
    r <- read.csv(ref_file) # second csv
    v <- read.table(vector_file, header = TRUE) # vector of DTW or Eukl. Distance
   
    # eukl vector
    # -----------
    #ev <- c(69.21276,62.81578,65.010155,61.710278,66.02131,70.359406,80.1752,74.889404,76.670494,75.99599,72.70532,72.27461,67.37461,76.04041,78.33756,78.44114,77.55197,78.84392,80.51587,81.67916,80.11759,79.95395,78.09905,75.77855,63.94236,58.139236,55.637978,58.9498,70.28792,69.30609,66.48689,68.00199,69.26568,67.74972,65.84293,65.7547,76.29288,67.58302,67.69207,69.071655,42.58431,16.371992,12.004663,48.457073,71.86843,74.62107,71.84624,69.749,64.33541,55.723553,59.451782,53.746376,63.17474,70.473076,67.57646,70.52761,73.28505,72.54642,69.01001,61.06474,64.83918,69.91638,67.64791,66.71376,34.913403,23.1052,34.42455,68.313156,76.30496,79.19704,74.26593,67.01217,58.230484,60.167347,51.79524,64.484245,64.72388,70.289894,69.71257,70.575836,67.17314,65.57329,70.69991,65.07381,68.83632,70.45966,64.990204,72.014305,54.993195,25.425587,46.35034,73.81238,78.37075,77.75709,72.89244,68.17551,59.14985,62.581272,61.940235,56.98722,63.98888,67.46914,67.08743,70.20914,70.68758,63.486397,65.498726,71.1684,67.19907,70.57879,71.876945,58.751884,29.83637,18.12425,51.42768,72.975,74.57922,76.87681,71.14292,64.06081,68.2263,60.67556,52.98061,58.372005,66.12254,75.427,67.408295,74.141075,71.11091,70.14357,67.756744,68.5575,65.82334,69.90328,50.281685,46.718544,18.07183,18.999876,58.476265,79.46169,75.306244,77.47093,73.76719,74.05868,69.21276,62.81578,65.010155,61.710278,66.02131,70.359406,80.1752,74.889404,76.670494,75.99599,72.70532,72.27461,67.37461,76.04041,78.33756,78.44114,77.55197,78.84392,80.51587,81.67916,80.11759,79.95395,78.09905,75.77855)
   
     # dtw vector
    # -----------
    #dv <- c(67.450165,61.8373,57.995403,58.842182,58.516678,65.28833,74.52684,79.20701,75.07553,77.41934,72.11982,75.45105,75.887344,76.04398,77.73156,77.481155,79.70588,78.84535,80.655426,79.28944,77.52765,77.49707,77.33842,78.2853,72.68763,54.88799,54.05448,58.47631,64.78829,64.80507,63.620316,63.84565,68.75749,63.406654,67.76686,64.682274,66.35599,69.27597,67.58,26.603455,13.3349695,19.725262,41.546272,77.23158,74.34425,75.43263,69.68541,62.933983,65.48347,56.027405,52.6231,65.210495,69.33193,68.506996,68.36391,69.968994,63.724064,63.352215,67.037125,70.03268,71.99411,71.311356,70.94061,61.27912,34.23092,33.256424,68.73742,74.86846,78.37462,72.827484,70.707146,73.32146,60.30156,55.005962,50.90196,54.1744,64.29834,60.46904,66.04209,67.76547,65.07523,70.774734,67.25412,69.03419,65.37344,64.42687,69.38517,57.34261,38.234913,51.45007,73.679276,74.58201,78.706566,72.717804,70.393486,65.01836,60.12496,52.99007,55.738518,58.5375,57.733536,66.105484,67.6524,70.618004,65.79678,62.679523,67.47142,66.8773,71.19047,69.09643,69.836914,63.78529,30.230482,25.960812,56.940697,70.21459,72.66414,76.363716,72.20411,65.2254,67.667885,60.71481,55.71124,55.094784,58.662174,66.57453,64.96771,70.32338,69.015785,71.03124,67.64186,68.38103,69.878235,63.884182,66.128975,45.236877,25.481417,66.169495,73.13383,75.798935,76.61905,78.464645,75.00377,74.76678,67.450165,61.8373,57.995403,58.842182,58.516678,65.28833,74.52684,79.20701,75.07553,77.41934,72.11982,75.45105,75.887344,76.04398,77.73156,77.481155,79.70588,78.84535,80.655426,79.28944,77.52765,77.49707,77.33842,78.2853)
    
    # test vector
    # -----------
    #tv <- c (1,10,30,40,45,50,70,78,90,102,106,105,100,80,83,89,97,70,60,40,30,20,4,2,1,10,30,40,45,50,70,78,90,102,106,105,100,80,83,89,97,70,60,40,30,20,4,2,1,10,30,40,45,50,70,78,90,102,106,105,100,80,83,89,97,70,60,40,30,20,4,2,1,10,30,40,45,50,70,78,90,102,106,105,100,80,83,89,97,70,60,40,30,20,4,2,1,10,30,40,45,50,70,78,90,102,106,105,100,80,83,89,97,70,60,40,30,20,4,2,1,10,30,40,45,50,70,78,90,102,106,105,100,80,83,89,97,70,60,40,30,20,4,2,1,10,30,40,45,50,70,78,90,102,106,105,100,80,83,89,97,70,60,40,30,20,4,2)
    # https://dynamictimewarping.github.io/r/
    
    # sort data from first CSV by osm_id and day_date
    days <- as.data.frame(table(s$day_date))$Var1
    aa <- s[s$osm_id==seg,]
    a <- aa[order(aa$date_time),]
  
    # sort data from second CSV by osm_id and day_date
    days2 <- as.data.frame(table(r$day_date))$Var1
    xx <- r[r$osm_id==seg,]
    x <- xx[order(xx$date_time),]
    
    # necessary information for plot-labeling
    road_seg <- paste('A5-',seg,sep='')
    road_seg_fig <-paste('fig/',road_seg, '.pdf',sep='')        
    num_weeks <- round(dim(a)[1]/336)  
    num_weeks2 <- round(dim(x)[1]/336) 
    lane_number <- unique(a$lane_quantity)
    lane_number2 <- unique(x$lane_quantity)
    max_velocity <- max(as.numeric(a$avg_vs), na.rm=TRUE)
    max_velocity2 <- max(as.numeric(x$avg_vs), na.rm=TRUE)
    
    # test of dtw-package
    #alignment <- dtw(ev, tv, dist.method =  "Euclidean")
    
    # print to console
    cat('Road A5','Segment:',seg, 'Num weeks:', num_weeks, 'Lane quantity:', lane_number, 'Max Speed:', max_velocity,  '\n')
    cat('Reference','Segment:',seg, 'Num weeks:', num_weeks2, 'Lane quantity:', lane_number2, 'Max Speed:', max_velocity2,  '\n')
    
    # details to draw a diagram
    #   x=c(0,168) --> hours (a day) in a week
    #   x=c(0,23 --> hours a day
    #       plot(x=c(0,23),y=c(0,max_speed), type = 'b', col = 'blue', main=paste(road_seg,'TEST'), xlab="Hour/Day in a Week", ylab='Average Velocity');
    if(measure == 'speed') {
        max_speed <- max(as.numeric(a$avg_vs), na.rm=TRUE)
        plot(x=c(0,168),y=c(0,max_speed), main=paste(road_seg,'Avg Speed'), xlab="Hour in a Week", ylab='Average Velocity');
      } else if (measure == 'car') {
        max_autos_seg <- max(as.numeric(a$vehicle_count_per_segment), na.rm=TRUE)
        plot(x=c(0,168),y=c(0,max_autos_seg), main=paste(road_seg,'No. of vehicles'), xlab="Cars in a Week", ylab='Number of Vehicles' );
      } else if (measure == 'reference') {
        max_speed_ref <- max(as.numeric(x$avg_vs), na.rm=TRUE)
        plot(x=c(0,168),y=c(0,max_speed_ref), main=paste(road_seg,'Reference Speed'), xlab="Hours in a Week", ylab='Avg Velocity' );
      } else if (measure == 'mixing') {
        max_speed_ref <- max(as.numeric(x$avg_vs), na.rm=TRUE)
        plot(x=c(0,168),y=c(0,max_speed_ref), main=paste(road_seg,'Reference Speed'), xlab="Hours in a Week", ylab='Avg Velocity and ' );
      } else if (measure == 'dtw') {
        plot(alignment,main="Warping function");
        lines(1:100-25,col="red")
      } else {
          # tbd
      }
      
      # printing plot 
      # [lines()can be print on plot(), but not the other way around!]
      for(j in seq(1, length(days)-7, by=7) ){
        week <- a[as.Date(a$day_date)>=as.Date(days[j]) & as.Date(a$day_date)<=as.Date(days[j+6]),]
        week2 <- x[as.Date(x$day_date)>=as.Date(days2[j]) & as.Date(x$day_date)<=as.Date(days2[j+6]),]
        
        # expecting a week is starting at 0 ~ sunday
        first_day <- weekdays(as.Date(days[j]))
        last_day <- weekdays(as.Date(days[j+6]))
        null_values <- max(week$vehicle_count_per_segment)
        # print to console
        # is.na() returns true if missing value (NA)
        if(is.na(null_values)){
          cat(j,' Date:',as.character(days2[j]),first_day, 'to Date:', as.character(days2[j+6]),last_day, ' * Week has NAs\n')
        } else {
          cat(j,' Date:',as.character(days2[j]),first_day, 'to Date:', as.character(days2[j+6]),last_day, '\n')
        }
        
        # plotting avg velocity of first CSV
        if(measure == 'mixing') {
          lines(week$avg_vs, col='royalblue') 
        } else {
          #
        }
        
      }
      # drawing vektor on top of plot
      lines(v, col='#DC143C', lwd = 3, )
      
      # attache legend to plot
      legend('bottomright', legend=c("Avg Velocity", "DTW/ED"), col=c("royalblue", "#DC143C"), lty=1:3, cex=0.8, title="Line types", text.font=4, bg='lightblue')
      pause()
}
