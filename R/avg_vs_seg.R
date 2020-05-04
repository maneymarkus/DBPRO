# Run with:
#  source("avg_vs_seg.R") 
#  then:
#     avg_vs_seg('a5.csv', '1711296', 'speed')
#     avg_vs_seg('a5.csv', '1711296', 'other')
#
#
avg_vs_seg <- function(data_file, seg, measure) {

library(DAAG) # for pause())
library(scales)
library(ggplot2)

    #s <- read.csv(data_file)
    s <- read.csv('D:/Dateien/Uni/Semester6/PRO - DBPRO/Daten/a5.csv')
  
    # dates starts in 2017-01-01 <-- Sunday
    # 
    days <- as.data.frame(table(s$day_date))$Var1
    aa <- s[s$osm_id==seg,]
    a <- aa[order(aa$date_time),]
    
    road_seg <- paste('A5-',seg,sep='')
    road_seg_fig <- paste('fig/',road_seg, '.pdf',sep='')        
    num_weeks <- round(dim(a)[1]/104)  # 104 weeks in two years
    lane_number <- unique(a$lane_quantity)
    #cat('Road A5','Segment:',seg[n], 'Num weeks:', num_weeks, 'Lane quantity:', lane_number, '\n')
    cat('Road A5','Segment:',seg, 'Num weeks:', num_weeks, 'Lane quantity:', lane_number, '\n')
    #pdf(road_seg_fig)
    
    if(measure == 'speed') {
         max_speed <- max(as.numeric(a$avg_vs), na.rm=TRUE)
         if(num_weeks <200) {
           plot(x=c(0,200),y=c(0,max_speed), main=paste(road_seg,'avg speed'), xlab="hour/day in a week", ylab='avg velocity' );
         } else {
           plot(x=c(0,400),y=c(0,max_speed), main=paste(road_seg,'avg speed (two or more ways)'), xlab="hour/day in a week", ylab='avg velocity' );
         }
      } else {
         max_autos_seg <- max(as.numeric(a$vehicle_count_per_segment), na.rm=TRUE)
         if(num_weeks <200) {
           plot(x=c(0,200),y=c(0,max_autos_seg), main=paste(road_seg,'No. of vehicles'), xlab="hour/day in a week", ylab='number of vehicles' );
         } else {
           plot(x=c(0,400),y=c(0,max_autos_seg), main=paste(road_seg,'No. of vehicles (two or more ways)'), xlab="hour/day in a week", ylab='number of vehicles' );
         }
      }    

      
      for(i in seq(1, length(days)-7, by=7) ){                          
        #  get one week of data per segment
        week <- a[as.Date(a$day_date)>=as.Date(days[i]) & as.Date(a$day_date)<=as.Date(days[i+6]),]
        first_day <- weekdays(as.Date(days[i]))
        last_day <- weekdays(as.Date(days[i+6]))    
        
        #print(week$vehicle_count_per_segment)
        null_values <- max(week$vehicle_count_per_segment)  
        #print(is.na(null_values))  
        if(is.na(null_values)){
            cat(i,' Date:',as.character(days[i]),first_day, 'to Date:', as.character(days[i+6]),last_day, 'WEEK HAS NAs\n')
        } else {    
            cat(i,' Date:',as.character(days[i]),first_day, 'to Date:', as.character(days[i+6]),last_day, '\n')
        }
        if(measure == 'speed') {
           lines(week$avg_vs, col='blue')
        } else {
           lines(week$vehicle_count_per_segment, col='blue')
        }
        #pause()  

      }
      # to save the plot
      #dev.off()
      #cat('Plot saved in:', road_seg_fig, '\n')

}

avg_vs_seg('a5.csv', '1711296', 'other')
