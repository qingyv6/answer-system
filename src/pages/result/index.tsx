import { View, Image } from '@tarojs/components'
import { AtButton} from 'taro-ui'
import headerBg from "../../assets/219194528_0_final.png";
import question_result from '../../data/question_results.json'
import questions from '../../data/questions.json'
import './index.scss'
import GlobalFooter from "../../GlobalFooter";
// eslint-disable-next-line import/first
import Taro from "@tarojs/taro";
import {getBestQuestionResult} from "../../utils/bizUtils";

/**
 * 结果页面
 */
export default ()=>{
  //获取答案
  const answerList = Taro.getStorageSync('answerList');
  if (!answerList || answerList.length < 1){
    Taro.showToast({
      title: '答案为空',
      icon: 'error',
      duration: 3000
    })
  }
  //获取答案列表
  const result = getBestQuestionResult(answerList,questions,question_result);
  // @ts-ignore
  return (
    <View className='resultPage'>
      <View className='at-article__h1 title'>
        {result.resultName}
      </View>
      <View className='at-article__h2 subTitle'>
        {result.resultDesc}
      </View>
      <AtButton className='enterBtn' type='primary' circle onClick={()=>{
        Taro.reLaunch({
          url: '/pages/index/index'
        })
      }}
      >返回主页</AtButton>
      <Image
        style='width: 400px;height: 300px;'
        src={headerBg}
      />
      <GlobalFooter />
    </View>
  );
};
