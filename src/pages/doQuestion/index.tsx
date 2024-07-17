import {useEffect, useState} from 'react'
import {View} from '@tarojs/components'
import {AtButton, AtRadio} from 'taro-ui'
import questions from '../../data/questions.json'

import './index.scss'
// eslint-disable-next-line import/first
import Taro from "@tarojs/taro";

/**
 * 答题页面
 */

export default () => {
  const question = questions[0];

  //当前题目序号（从1开始）
  const [current, setCurrent] = useState<number>(1);
  //当前题目
  const [currentQuestion, setCurrentQuestion] = useState(questions[0])
  const questionOptions = currentQuestion.options.map((option) => {
    return {
      label: `${option.key}.${option.value}`,
      value: option.key
    }
  })

  //序号变化时，更新当前题目和选项
  useEffect(() => {
    setCurrentQuestion(questions[current - 1])
  }, [current]);
  //当前答案
  const [currentAnswer, setCurrentAnswer]=useState<string>();
  //回答列表，记录当前题目和当前答案
  const [answerList]=useState<string[]>([])

  return (
    <View className='doQuestionPage'>
      <View className='at-article__h2 title'>
        {current}、{question.title}
      </View>
      <View className='options-wrapper'>
        <AtRadio
          value={currentAnswer}
          onClick={(value)=>{
            setCurrentAnswer(value);
            //记录答案
            answerList[current-1]=value;
          }}
          options={questionOptions}
        />
      </View>
      {current < questions.length && (
        <AtButton
          className='controlBtn'
          //回答不能为空
          disabled={!currentAnswer}
          type='primary'
          circle
          onClick={() => {
          setCurrent(current + 1)
        }}
        >
          下一题
        </AtButton>
      )}
      {current >= questions.length && (
        <AtButton
          className='controlBtn'
          //回答不能为空
          disabled={!currentAnswer}
          type='primary'
          circle
          onClick={()=>{
            //传递答案
            Taro.setStorageSync('answerList', answerList);
            //跳转结果页
            Taro.reLaunch({
              url: '/pages/result/index'
            })
          }}
        >
          查看结果
        </AtButton>
      )}
      {current > 1 && (
        <AtButton
          className='controlBtn'
          type='primary' circle
          onClick={() => {
          setCurrent(current - 1)
          }}
        >
          上一题
        </AtButton>
      )}
    </View>
  );
};
